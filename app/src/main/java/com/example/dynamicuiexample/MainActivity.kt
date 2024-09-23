package com.example.dynamicuiexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dynamicuiexample.response.ComponentType
import com.example.dynamicuiexample.response.RemoteModel
import com.example.dynamicuiexample.response.ResponseData
import com.example.dynamicuiexample.ui.theme.DynamicUIExampleTheme
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var apiInterface: ApiInterface? = null

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getExampleData()
    }

    private fun getApiInterface() {
        apiInterface = RetrofitInstance.getInstance().create(ApiInterface::class.java)
    }

    private fun getExampleData() {
        getApiInterface()
        val call = apiInterface!!.getExampleData()
        call.enqueue(object : Callback<RemoteModel> {
            override fun onResponse(call: Call<RemoteModel>, response: Response<RemoteModel>) {
                if (response.isSuccessful && response.body() != null) {
                    bindResponse(response.body()!!)
                }
            }

            override fun onFailure(call: Call<RemoteModel>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun bindResponse(body: RemoteModel) {
        setContent {
            val isButtonEnabled by viewModel.isStateValid.observeAsState(false)
            Column(Modifier.fillMaxSize()) {
                setView(data = body.data)

                SaveButton(isButtonEnabled)
            }
        }
    }

    @Composable
    private fun SaveButton(isButtonEnabled: Boolean) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White,
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            enabled = isButtonEnabled,
            onClick = {

            }) {
            Text(text = "Save Button")
        }
    }
}

@Composable
private fun setView(data: ArrayList<ResponseData>) {
    data.forEach { value ->
        checkUiType(value)
    }
}

@Composable
private fun checkUiType(value: ResponseData, viewModel: MainViewModel = hiltViewModel()) {
    viewModel.setMandatoryFields(value)
    when (value.type) {
        ComponentType.COLUMN -> showColumn(value = value)
        ComponentType.ROW -> showRow(value = value)
        ComponentType.TEXT -> showText(value = value)
        ComponentType.RADIO_BUTTON -> showRadio(value = value, viewModel = viewModel)
        ComponentType.RADIO_GROUP -> showRadioGroup(value = value)
        else -> Spacer(modifier = Modifier.height(1.dp))
    }
}

@Composable
fun showColumn(value: ResponseData) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        setView(data = value.children)
    }
}

@Composable
fun showRow(value: ResponseData) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        setView(data = value.children)
    }
}


@Composable
fun showText(value: ResponseData) {
    Text(
        text = value.value
    )
}


@Composable
fun showRadio(
    value: ResponseData,
    viewModel: MainViewModel
) {
    val selectedRadioId by viewModel.selectedRadio.observeAsState(initial = "")

    RadioButton(selected = value.id == selectedRadioId,
        onClick = {
            viewModel.onRadioSelected(id = value.id, selectionId = value.selectionId)
        })

}

@Composable
fun showRadioGroup(
    value: ResponseData
) {
    Column(
        modifier = Modifier.padding(start = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        setView(data = value.children)
    }
}

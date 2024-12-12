package com.example.vivemurcia.views.home

import androidx.compose.material3.Tab
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vivemurcia.R
import com.example.vivemurcia.data.TabItem
import com.example.vivemurcia.ui.theme.fondoPantalla


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InicioHome() {

    Column(
        Modifier
            .fillMaxSize()
            .background(fondoPantalla)
            .padding(top = 16.dp)) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.Gray))
        {
            FiltroActividades()
        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White)) {
            CategoriasTab()
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(horizontal = 16.dp))  {
        }
    }
}

@Composable
fun CategoriasTab() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    
    val tabs = listOf(
        TabItem("Aventura", R.drawable.atle),
        TabItem("Cocina", R.drawable.cocina),
        TabItem("Relax", R.drawable.relax),
        TabItem("Arte", R.drawable.arte)
    )

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab( text = { Text(tabs[index].title, color = Color.Black) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    icon = {
                        Icon(
                            painter = painterResource(id = tabs[index].icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                )
            }
        }
    }


}



@Composable
fun FiltroActividades(){

}
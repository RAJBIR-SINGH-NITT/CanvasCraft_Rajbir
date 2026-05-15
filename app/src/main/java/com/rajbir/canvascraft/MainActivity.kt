package com.rajbir.canvascraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
//import com.rajbir.canvascraft.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //FloatState is the way to handle slider numbers
            //it is a variable that store data,and it starts from 8
            var gapSize by remember { mutableFloatStateOf(8f) }
            var roundness by remember { mutableFloatStateOf(0f) }

            ///IntState is the way to handle image number
            /// like for whole numbers like image resource IDs
            var image1 by remember{mutableIntStateOf(R.drawable.pic_three)}
            var image2 by remember{mutableIntStateOf(R.drawable.pic_four)}


            //this makes the screen fill completely and sets  white background
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "           Rajbir Editing studio          ",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text="                                   Nit trichy is my college                   ",
                        style=MaterialTheme.typography.bodySmall
                    )

                    // This is my collage area
                    TwoGridVerticalLayout(
                        image1 = image1,
                        image2 = image2,   /// first i had done R.drawable.pic_three,but we are passing the drawable directly, not your variable.

                        spacing = gapSize.dp,
                        cornerRadius = roundness.dp,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Controls Section
                    Text(text = "Padding: ${gapSize.toInt()}dp")

                    //This slider controls the gap between the images
                    //I kept max as 60 bcz more than tht  looks weird
                    Slider(
                        value = gapSize,
                        onValueChange = { gapSize = it },
                        valueRange = 0f..60f   /// i want to go upto 60
                    )



                    Spacer(modifier = Modifier.height(8.dp))  // this  will add empty space of 8dp between the gap slider above and corner text below

                    Text(text = "Corner Radius: ${roundness.toInt()}dp")/// live text on the screen will be shown by this
                    Slider(
                        value = roundness,//// variable is neccecary for the slider position
                        onValueChange = { roundness = it },/// drag to  update  the  variable
                        valueRange = 0f..100f   //i want to go upto 100
                    )

                    /// i am putting all button(swap,reset) inside the row so that there will some definite order
                    Row {
                        // reset button stays here exactly as is
                        // swap button stays here exactly as is]
                        /// i had used temp method for swapping
                        Button(
                            onClick = {
                                val temp = image1
                                image1 = image2
                                image2 = temp
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Green
                            )
                        ) {
                            Text("Swap Images")
                        }
                        /// this is reset button  that will be used by users to reset the previous setting
                        ///gapSize = 8.5f  roundness = 0.5f
                        /// most of people start with 8f and 0f but i  had tried to start with 0.5f and 8.5f
                        Button(
                            onClick = {
                                gapSize = 8.5f
                                roundness = 0.5f
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            )
                        ) {
                            Text("reset")
                        }
                    }
                    Text(
                        text = "*         MADE IN INDIA BY INDIAN,RAJBIR THE MASTERMIND          *",
                        style = MaterialTheme.typography.bodySmall
                    )




                }
            }
        }
    }
}
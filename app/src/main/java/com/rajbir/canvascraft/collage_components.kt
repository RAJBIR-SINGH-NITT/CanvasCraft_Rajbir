package com.rajbir.canvascraft

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
//// This will take two image and put it one above other in the screen
// and also it take spacing and cornerRadius values from the mainActivity to control the look of the screen
fun TwoGridVerticalLayout(
    image1: Int,
    image2: Int,
    gridSize: Int,
    spacing: Dp,
    cornerRadius: Dp,
    modifier: Modifier = Modifier
) {
    if (gridSize == 1) {
        Column(modifier = modifier.fillMaxSize()) {
            // Image Box(top)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(bottom = spacing / 2)
                    .clip(RoundedCornerShape(cornerRadius))
            ) {
                //  this will take one image and make it like that  way so that we can see in the screen
                ///we can zoom,rotate,change all the configuration etc..
                RajbirImage(imageRes = image1)
            }

            // image box(bottom)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(top = spacing / 2)
                    .clip(RoundedCornerShape(cornerRadius))
            ) {
                // we are using the custom gesture component here
                RajbirImage(imageRes = image2)
            }
        }
    } else {
        val imagePool = listOf(image1, image2)
        Column(modifier = modifier.fillMaxSize()) {
            for (row in 0 until gridSize) {
                Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    for (col in 0 until gridSize) {
                        val imageIndex = (row * gridSize + col) % imagePool.size
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(spacing / 2)
                                .clip(RoundedCornerShape(cornerRadius))
                        ) {
                            RajbirImage(imageRes = imagePool[imageIndex])
                        }
                    }
                }
            }
        }
    }
}

@Composable
/// scale,rotation,offset these are  the three variables remember the current state of the image
///scale =  for the  zooming purpose
///rotation = for the rotation purpose
///offset = to move from the original position
fun RajbirImage(imageRes: Int) {
    var scale by remember { mutableFloatStateOf(1f) }
    var rotation by remember { mutableFloatStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "User Collage Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            /// we are doing this for all the tranformation to the image  visually
            /// the value we are giving like scale,rotaion it will show in the screen
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
            .pointerInput(Unit) {
                /// detectTransformGestures  is to listen movement of fingers
                detectTransformGestures { _, pan, zoom, angle ->
                    scale = (scale * zoom).coerceIn(0.5f, 5.5f) /// .coerceIn is done so that this will limit the zoom
                    /// this will not zoom out  too much nor zoom in too much remain in 0.5f to 8.5f
                    ///i kept min as 0.5 bcz going below that makes image too small to see and max 5.5 bcz more than that looks too zoomed
                    rotation += angle
                    offset += pan
                }
            }
    )
}
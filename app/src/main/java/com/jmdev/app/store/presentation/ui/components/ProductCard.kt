package com.jmdev.app.store.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jmdev.app.store.model.Product

@Composable
fun ProductCard(product: Product, navToDetail: (Int) -> Unit = {}, enabled: Boolean = true) {
    Card(
        enabled = enabled,
        onClick = {
            navToDetail(product.id)
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp)
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .size(120.dp)
                    .background(Color.White)
                    .padding(12.dp), contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = product.title,
                    modifier = Modifier.size(120.dp)
                )
            }
            Column {
                Text(
                    product.title,
                    modifier = Modifier.padding(start = 12.dp),
                    style = TextStyle(fontSize = 18.sp),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    product.description,
                    modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                    style = TextStyle(fontSize = 14.sp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.PriceChange, "")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${product.price}$")
                }

            }
        }
    }
}
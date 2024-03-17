package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.ui.theme.MovieAppTheme

import kotlin.random.Random
import android.content.Context
import android.os.Debug
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

val ListStarring1 = arrayOf("Ewan McGregor", "Natalie Portman", "Hayden Christensen");
val Movie1 = Movie("Star Wars III", "star_wars_3.jpeg",
    "LucasFilm", "Three years into the Clone Wars, Obi-Wan pursues a new threat," +
            " while Anakin is lured by Chancellor Palpatine into a sinister plot to rule the galaxy.",
    ListStarring1, 140, Random.nextInt(0, 16), 0)

val ListStarring2 = arrayOf("Cillian Murphy", "Emily Blunt", "Robert Downey Jr", "Matt Damon");
val Movie2 = Movie("Oppenheimer", "oppenheimer.jpeg",
    "Universal Pictures", "The story of the development of the atomic bomb by the Manhattan Project during World War II.",
    ListStarring2, 180, Random.nextInt(0, 16), 0)

val ListStarring3 = arrayOf("Tom Holland", "Mark Wahlberg", "Antonio Banderas");
val Movie3 = Movie("Uncharted", "uncharted.jpeg",
    "Columbia Pictures", "Street-smart Nathan Drake is recruited by seasoned treasure hunter Victor \"Sully\" Sullivan to recover a fortune amassed by Ferdinand Magellan.",
    ListStarring3, 116, Random.nextInt(0, 16), 0)

val ListStarring4= arrayOf("Yoshitsugu Matsuoka", "Natsuki Hanae", "Hiro Shimono", "Akari Kitô", "Kengo Kawanishi");
val Movie4 = Movie("Demon Slayer", "demon_slayer.jpeg",
    "Aniplex", "After his family was brutally murdered and his sister turned into a demon, Tanjiro Kamado's journey as a demon slayer began.",
    ListStarring4, 119, Random.nextInt(0, 16), 2)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "mainPage") {
                composable("mainPage") { MainPage(navController) }
                composable("moviePage/{movieName}") { backStackEntry ->
                    val movieName = backStackEntry.arguments?.getString("movieName")
                    val movie = getMovieByName(movieName) // Vous devez définir cette fonction pour obtenir le film par son nom
                    if (movie.seats_selected > 0)
                    {
                        MoviePage(movie, true, navController)
                    }
                    else
                    {
                        MoviePage(movie, false, navController)
                    }
                }
            }
        }
    }
}

fun getMovieByName(movieName: String?): Movie {
    if (movieName == "Star Wars III") {
        return Movie1
    }
    else if (movieName == "Oppenheimer") {
        return Movie2
    }
    else if (movieName == "Uncharted") {
        return Movie3
    }
    else if (movieName == "Demon Slayer") {
        return Movie4
    }
    else {
        return Movie1
    }
}

fun getImageMovieByName(movieName: String?): Int {
    if (movieName == "Star Wars III") {
        return R.drawable.star_wars_3
    }
    else if (movieName == "Oppenheimer") {
        return R.drawable.oppenheimer
    }
    else if (movieName == "Uncharted") {
        return R.drawable.uncharted
    }
    else if (movieName == "Demon Slayer") {
        return R.drawable.demon_slayer
    }
    else {
        return R.drawable.star_wars_3
    }
}


@Composable
fun MovieCard(movie: Movie, navController: NavController) {
    var IdImage: Int? = null;
    IdImage = getImageMovieByName(movie.name)
    Card(
        modifier = Modifier
            .width(170.dp) // Adjust the width
            .padding(6.dp),

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = IdImage!!),
                contentDescription = "Movie Image",
                modifier = Modifier
                    .height(100.dp) // Adjust the height
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = movie.name,
                    color = Color.LightGray,
                    style = TextStyle(fontSize = 16.sp) // Adjust the font size
                )

                val hours = movie.running_time_mins / 60
                val minutes = movie.running_time_mins % 60
                Text(
                    text = "${hours}h ${minutes}min",
                    color = Color.LightGray,
                    style = TextStyle(fontSize = 12.sp) // Adjust the font size
                )

                Box(modifier = Modifier.height(16.dp))
                {

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,)
                {
                    if (movie.seats_selected == 0) {
                        Image(
                            painter = painterResource(id = R.drawable.seatw),
                            contentDescription = "Cinema Image",
                            modifier = Modifier
                                .height(20.dp) // Adjust the height
                                .width(20.dp),
                            contentScale = ContentScale.Crop
                        )

                        Text(text = "${movie.seats_remaining} seats remaining",
                            color = Color.White,
                            style = TextStyle(fontSize = 12.sp)
                        ) // Adjust the font size
                    }
                    else
                    {
                        Image(
                            painter = painterResource(id = R.drawable.seatg),
                            contentDescription = "Cinema Image",
                            modifier = Modifier
                                .height(20.dp) // Adjust the height
                                .width(20.dp),
                            contentScale = ContentScale.Crop
                        )

                        Text(text = "${movie.seats_selected} seats selected",
                            color = Color.Green,
                            style = TextStyle(fontSize = 12.sp)
                        ) // Adjust the font size
                    }

                }
                Box(modifier = Modifier.height(16.dp))
                {

                }

                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth())
                {
                    Button(onClick = {
                        navController.navigate("moviePage/${movie.name}") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(contentColor = Color.Black,containerColor = Color.Black),)

                    {
                        Text("Watch",
                            color = Color.White,
                            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.ExtraBold) // Adjust the font size
                        )
                    }
                }
            }
        }
    }
}

fun getMovieImageByName(movieName: String?): Int {
    if (movieName == "Star Wars III") {
        return R.drawable.fight_star_wars
    }
    else if (movieName == "Oppenheimer") {
        return R.drawable.oppeinhamer_movie
    }
    else if (movieName == "Uncharted") {
        return R.drawable.uncharted_movie
    }
    else if (movieName == "Demon Slayer") {
        return R.drawable.demon_slayer_movie
    }
    else {
        return R.drawable.star_wars_3
    }
}


@Composable
fun MoviePage(movie: Movie, verif: Boolean = false, navController: NavController)
{
    var text by remember { mutableStateOf("${movie.seats_remaining} seats remaining") }
    var text2 by remember { mutableStateOf("${movie.seats_selected}") }
    MovieAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight(),
            color = Color.Black,
        ) {

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
            )
            {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.Start
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "Return",
                        modifier = Modifier
                            .height(30.dp) // Adjust the height
                            .width(30.dp)
                            .clickable(onClick = {
                                if (verif) {
                                    navController.navigate("mainPage")
                                } else {
                                    navController.popBackStack()
                                }
                            }),
                        contentScale = ContentScale.Crop)

                }
                Image(
                    painter = painterResource(id = getMovieImageByName(movie.name)),
                    contentDescription = "Movie Image",
                    modifier = Modifier
                        .height(300.dp) // Adjust the height
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Box(modifier = Modifier.height(16.dp) )
                {

                }
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = movie.name,
                    color = Color.White,
                    style = TextStyle(fontSize = 30.sp, fontFamily = FontFamily(Font(R.font.quicksand)), fontWeight = FontWeight.ExtraBold) // Adjust the font size
                )
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),){
                    Text(text = "Starring: ",
                        color = Color.White,
                        style = TextStyle(fontSize = 16.sp ,fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily(Font(R.font.quicksand))) // Adjust the font size
                    )

                    Text(
                        text = movie.starring.joinToString(),
                        color = Color.White,
                        style = TextStyle(fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.quicksand)) ) // Adjust the font size
                    )
                }
                Box(modifier = Modifier.height(8.dp) )
                {

                }
                val hours = movie.running_time_mins / 60
                val minutes = movie.running_time_mins % 60
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),){
                    Text(text = "Running Time: ",
                        color = Color.White,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily(Font(R.font.quicksand))) // Adjust the font size
                    )
                    Text(text = "${hours}h ${minutes}mins",
                        color = Color.White,
                        style = TextStyle(fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.quicksand))) // Adjust the font size
                    )
                }
                Box(modifier = Modifier.height(8.dp) )
                {

                }
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = movie.description,
                    color = Color.White,
                    style = TextStyle(fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.quicksand))) // Adjust the font size
                )

                Box(modifier = Modifier.height(16.dp) )
                {

                }
                if (verif)
                {
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                        horizontalArrangement = Arrangement.End)
                    {
                        Image(
                            painter = painterResource(id = R.drawable.seatg),
                            contentDescription = "Cinema Image",
                            modifier = Modifier
                                .height(35.dp) // Adjust the height
                                .width(35.dp),
                            contentScale = ContentScale.Crop
                        )
                        Box(modifier = Modifier.width(5.dp) )
                        {

                        }
                        Text(text ="${movie.seats_selected} seats selected",
                            color = Color.Green,
                            style = TextStyle(fontSize = 20.sp)
                        )
                    }
                }
                else
                {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly)
                    {
                        Text(text ="Select Seats",
                            color = Color.White,
                            style = TextStyle(fontSize = 16.sp)
                        )
                        // Minus button
                        var Id_image2: Int? = null
                        if (movie.seats_selected == 0) {
                            Id_image2 = R.drawable.minusg
                        }
                        else
                        {
                            Id_image2 = R.drawable.minusw
                        }
                        Image(

                            painter = painterResource(id = Id_image2!!),
                            contentDescription = "Cinema Image",
                            modifier = Modifier
                                .height(20.dp) // Adjust the height
                                .width(20.dp)
                                .clickable(onClick = {
                                    if (movie.seats_selected > 0) {
                                        movie.seats_remaining += 1
                                        movie.seats_selected -= 1
                                        text = "${movie.seats_remaining} seats remaining"
                                        text2 = "${movie.seats_selected}"
                                    }
                                }),
                            contentScale = ContentScale.Crop,
                        )

                        Text(text = text2,
                            color = Color.White,
                            style = TextStyle(fontSize = 16.sp)
                        )
                        // Plus button
                        var Id_image: Int? = null
                        if (movie.seats_remaining == 0) {
                            Id_image = R.drawable.plusg
                        }
                        else
                        {
                            Id_image = R.drawable.plusw
                        }
                        Image(

                            painter = painterResource(id = Id_image!!),
                            contentDescription = "Cinema Image",
                            modifier = Modifier
                                .height(20.dp) // Adjust the height
                                .width(20.dp)
                                .clickable(onClick = {
                                    if (movie.seats_remaining > 0) {
                                        movie.seats_remaining -= 1
                                        movie.seats_selected += 1
                                        text = "${movie.seats_remaining} seats remaining"
                                        text2 = "${movie.seats_selected}"
                                    }
                                }),
                            contentScale = ContentScale.Crop,
                        )

                        Image(
                            painter = painterResource(id = R.drawable.seatw),
                            contentDescription = "Cinema Image",
                            modifier = Modifier
                                .height(20.dp) // Adjust the height
                                .width(20.dp),
                            contentScale = ContentScale.Crop
                        )
                        Text(text = text,
                            color = Color.White,
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainPage(navController: NavController){
    MovieAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black,
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            )
            {
                //Title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Text(
                        modifier = Modifier.padding(8.dp)
                            ,
                        text = "MoviesApp",
                        color = Color.White,
                        style = TextStyle(fontSize = 30.sp, ) // Adjust the font size
                    )
                }
                Box(modifier = Modifier.height(16.dp) )
                {

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    MovieCard(movie = Movie1,navController = navController)
                    MovieCard(movie = Movie2,navController = navController)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly)
                {
                    MovieCard(movie = Movie3, navController = navController)
                    MovieCard(movie = Movie4, navController = navController)
                }

            }

        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieAppTheme {
        Greeting("Android")
    }
}
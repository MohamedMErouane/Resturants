package com.example.problem

import DBHelper
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.problem.ui.theme.ProblemTheme

/*
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
*/


class MainActivity : ComponentActivity() {
    private val cartItems = mutableStateListOf<CartItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProblemTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "Login") {
                    composable(route = "Login") {
                        Login(navController)
                    }

                    composable(
                        route = "second",

                    ) {
                        Secondscreen(navController)
                    }
                    composable(
                        route="Profile"

                    ){

                        ProfileScreen(navController,this@MainActivity)
                    }
                    composable (
                        route="category/Pizza"
                            ){
                        PizzaScreen(navController = navController,this@MainActivity)
                    }

                    composable (
                        route="category/Tacos"
                    ){
                        TacosScreen(navController = navController)
                    }
                    composable (
                        route="category/Hamburger"
                    ){
                        BurgerScreen(navController = navController)
                    }

                    composable (
                        route="category/Glace"
                    ){
                        GlaceScreen(navController = navController)
                    }
                    composable (
                        route="category/Drink"
                    ){
                        DrinkScreen(navController = navController)
                    }
                }
            }
        }
    }

    fun addToCart(item: Category) {
        val existingItem = cartItems.find { it.category == item }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(item))
        }
    }

    fun getCartItems(): List<CartItem> = cartItems
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var textfield by remember { mutableStateOf("Login") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val dbHelper = remember { DBHelper(context) }
    val username1 = "Mohamed"
    val password2 = "2004"
    val inserted = dbHelper.InsertData(username1, password2)
    if (inserted) {
        println("Username and password inserted successfully.")
    } else {
        println("Failed to insert username and password.")
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Spacer(modifier = Modifier.height(50.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(230.dp)
            )

            Text(
                text = "Login",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) {
                    PasswordVisualTransformation()
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            imageVector = if (passwordVisible) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            },
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                    if (username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Please enter all info", Toast.LENGTH_SHORT).show()
                    } else {
                        val result = dbHelper.CheckUserNamePassword(username, password)
                        if (result) {
                            textfield = "Login successfully"
                            navController.navigate("second")
                        } else {
                            Toast.makeText(context, "Username or password incorrect", Toast.LENGTH_SHORT).show()                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Login")
            }
        }

        Text(
            text = textfield,
            fontSize = 16.sp,
            color = Color.Red,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
/*
fun validate(username: String, password: String): Boolean {
    val originusername = "Mohamed"
    val originpassword = "2004"

    return username == originusername && password == originpassword
}*/
data class Category(val name: String, val imageId: Int, val price: Double)

val categories = listOf(
    Category("Pizza", R.drawable.pizza, 10.99),
    Category("Tacos", R.drawable.tacos, 8.99),
    Category("Hamburger", R.drawable.hamburger, 6.99),
    Category("Drink", R.drawable.coca, 6.99),
    Category("Glace", R.drawable.glass3, 6.99)


)
@Composable
fun CategoryItem(category: Category, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = category.imageId),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.name,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Black
            )
            Text(
                text = "Price: $${category.price}",
                color = Color.Gray
            )
            Button(onClick = {
                navController.navigate("category/${category.name}")
            }, modifier = Modifier.padding(top = 8.dp)) {
                Text(text = "+")
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Secondscreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Welcome!") },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("second") }
                    ) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                    IconButton(
                        onClick = { navController.navigate("Profile")}
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("Hello ", modifier = Modifier.padding(16.dp))
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(categories) { category ->
                    CategoryItem(category = category, navController = navController)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }, modifier = Modifier.padding(16.dp)) {
                Text(text = "Return")
            }
        }
    }
}
data class CartItem(val category: Category, var quantity: Int = 1)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, mainActivity: MainActivity) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Welcome!") },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("second") }
                    ) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                    IconButton(
                        onClick = { navController.navigate("Profile")}
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text("Panier Page")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Items in Cart:")
            mainActivity.getCartItems().forEach { cartItem ->
                Text("- ${cartItem.category.name} x${cartItem.quantity}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { sendEmail(mainActivity, mainActivity.getCartItems()) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Buy")
            }
        }
    }
}


val Pizza = listOf(
    Category("Margerita", R.drawable.margerita, 10.99),
    Category("Mozzarella", R.drawable.mozzarella, 8.99),
    Category("pepperoni", R.drawable.pepperoni, 6.99),
    Category("Margerita", R.drawable.pizza, 10.99),
    Category("Mozzarella", R.drawable.mozzarella, 8.99),
    Category("Margerita", R.drawable.margerita, 10.99),
    Category("Mozzarella", R.drawable.mozzarella, 8.99)


)

@Composable
fun PizzaItem(category: Category, navController: NavController, mainActivity: MainActivity) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = category.imageId),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp)
        )
        Text(
            text = category.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Price: $${category.price}",
            fontSize = 18.sp,
            color = Color.Gray
        )
        Button(
            onClick = {  mainActivity.addToCart(category) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Buy")
        }
    }
}

@Composable
fun PizzaScreen(navController: NavController,mainActivity: MainActivity) {
    Text(
        text = "Pizza Page",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
    LazyColumn {
        items(Pizza) { category ->
            PizzaItem(category = category,navController = navController, mainActivity)
        }
    }
}
//tacos Screeen


val tacos = listOf(
    Category("tacos", R.drawable.tacos, 10.99),
    Category("taco1", R.drawable.tacos1, 8.99),
    Category("tacos4", R.drawable.tacos4, 6.99),
    Category("tacos", R.drawable.tacos, 10.99),
    Category("tacos1", R.drawable.tacos1, 8.99),
    Category("tacos4", R.drawable.tacos4, 10.99)


)
@Composable
fun TacosItem(category: Category, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = category.imageId),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp)
        )
        Text(text = category.name, fontSize = 18.sp)
        Text(text = "Price: $${category.price}", fontSize = 16.sp)
        Button(onClick = {
        }, modifier = Modifier.padding(8.dp)) {
            Text(text = "Buy")
        }
    }
}
@Composable
fun TacosScreen(navController: NavController){
    Text(text = "Tacos Page")
    LazyColumn {
        items(tacos) { category ->
            TacosItem(category = category, navController = navController)
        }
    }
}
// Burger Screen


val burger = listOf(
    Category("hamburger", R.drawable.hamburger, 10.99),
    Category("Turkey Burger", R.drawable.turkeyburger, 8.99),
    Category("burger", R.drawable.burgert, 6.99),
    Category("ChessBurger", R.drawable.cheeseburger, 10.99),
    Category("lamburger", R.drawable.lambburger, 8.99),
    Category("burger2", R.drawable.burger2, 10.99)


)
@Composable
fun BurgerItem(category: Category, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = category.imageId),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp)
        )
        Text(
            text = category.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Price: $${category.price}",
            fontSize = 18.sp,
            color = Color.Gray
        )
        Button(
            onClick = { /* Handle buy action */ },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Buy")
        }
    }
}

@Composable
fun BurgerScreen(navController: NavController) {
    Text(
        text = "Burger Page",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
    LazyColumn {
        items(burger) { category ->
            BurgerItem(category = category, navController = navController)
        }
    }
}
// Glace Screen


val glace = listOf(
    Category("glace", R.drawable.glace3, 10.99),
    Category("glace1 ", R.drawable.chocoloat, 8.99),
    Category("glace2", R.drawable.glace4, 6.99),
    Category("choclate", R.drawable.glace2, 10.99),
    Category("vanila", R.drawable.glace5, 8.99),
    Category("glace3", R.drawable.glass2, 10.99)


)
@Composable
fun GlaceItem(category: Category, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = category.imageId),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp)
        )
        Text(
            text = category.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Price: $${category.price}",
            fontSize = 18.sp,
            color = Color.Gray
        )
        Button(
            onClick = { /* Handle buy action */ },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Buy")
        }
    }
}

@Composable
fun GlaceScreen(navController: NavController) {
    Text(
        text = "Glace Page",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
    LazyColumn {
        items(glace) { category ->
            GlaceItem(category = category, navController = navController)
        }
    }
}
//Drink screen
val drink = listOf(
    Category("Coca-Cola", R.drawable.coca, 10.99),
    Category("Fanta", R.drawable.fanta, 8.99),
    Category("Hawaiian Punch", R.drawable.hawai, 6.99),
    Category("Mixed Juice", R.drawable.mixed, 10.99),
    Category("Orange Juice", R.drawable.orange, 8.99),
    Category("Pomegranate Juice", R.drawable.poms, 10.99)
)

@Composable
fun DrinkItem(category: Category, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = category.imageId),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp)
        )
        Text(
            text = category.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Price: $${category.price}",
            fontSize = 18.sp,
            color = Color.Gray
        )
        Button(
            onClick = { /* Handle buy action */ },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Buy")
        }
    }
}

@Composable
fun DrinkScreen(navController: NavController) {
    Text(
        text = "Drinks",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
    LazyColumn {
        items(drink) { category ->
            DrinkItem(category = category, navController = navController)
        }
    }
}

fun sendEmail(context: Context, cartItems: List<CartItem>) {
    val emailIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_EMAIL, arrayOf("jackdoeno@gmail.com"))
        putExtra(Intent.EXTRA_SUBJECT, "Purchase Notification")
        val emailContent = StringBuilder()
        emailContent.append("You've purchased the following items:\n\n")
        cartItems.forEach { cartItem ->
            emailContent.append("- ${cartItem.category.name} x${cartItem.quantity}\n")
        }
        putExtra(Intent.EXTRA_TEXT, emailContent.toString())
    }
    context.startActivity(emailIntent)
}
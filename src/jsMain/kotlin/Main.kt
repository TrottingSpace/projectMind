import androidx.compose.runtime.*
import kotlinx.browser.window
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import kotlin.random.Random

val trades = listOf("Wine", "Whiskey", "Rum", "Beer", "Tequila")
val matches = (0..4).map{trades[Random.nextInt(4)]}

fun main() {
    var currentRound by mutableStateOf(0)
    val choices = mutableStateListOf<List<String>>()
    var savedChoices = mutableStateListOf("", "", "", "", "")
    var isWin by mutableStateOf(0)

    renderComposable(rootElementId = "root") {
        val boxH: Int = window.innerHeight / 8
        val boxW: Int = window.innerWidth / 5
        val boxL: Int = if (boxH > boxW) { boxW } else { boxH }
        console.log("Width", window.innerWidth)
        console.log("Height", window.innerHeight)
        Table({
            style {
                border(3.px, LineStyle.Solid, Color.blueviolet)
                fontSize((boxL * 0.6).px)
                textAlign("center")
                property("vertical-align", "center")
            }
        }){
            Tr({style{height((boxL * 0.9).px)}}){
                for (i in 0..4){
                    Td({
                        style{
                            width((boxL * 0.9).px)
                            border(3.px, LineStyle.Solid, Color.blueviolet)
                        }
                    }){
                        Text(i.toString())
                    }
                }
            } //Tr end
            Tr({style{height((boxL * 0.9).px)}}){ //round 1
                for (i in 0..4){
                    Td({style{width((boxL * 0.9).px)}}){
                        Select({
                            style { fontSize((boxL * 0.17).px) }
                            if (currentRound > 0) { disabled() }
                            onChange { savedChoices[i] = it.value!! }
                        }) {
                            Option(""){Text("Select")}
                            trades.forEach {
                                Option(it){
                                    Text(it)
                                }
                            }
                        }
                    } //Td end
                } //for end
            } //Tr end
            if (currentRound > 0) {
                val unmatched = matches.mapIndexedNotNull{index, it ->
                    if (it == choices[0][index]){ null }
                    else { it }
                }
                Tr({style{height((boxL * 0.9).px)}}){ //round 1 check
                    for (i in 0..4) {
                        Td({style{width((boxL * 0.9).px)}}){
                            if (matches[i] == choices[0][i]) {
                                Text("✅")
                            }
                            else if (unmatched.contains(choices[0][i])) {
                                Text("🔁")
                            }
                            else {
                                Text("❌")
                            }
                        } //Td end
                    } //for end
                } //Tr end
                Tr({style{height((boxL * 0.9).px)}}){ //round 2
                    for (i in 0..4){
                        Td({style{width((boxL * 0.9).px)}}){
                            Select({
                                style { fontSize((boxL * 0.17).px) }
                                if (currentRound > 1 || matches[i] == choices[0][i]) { disabled() }
                                onChange { savedChoices[i] = it.value!! }
                            }) {
                                if (matches[i] == choices[0][i]) {
                                    Option(choices[0][i]) { Text(choices[0][i]) }
                                    savedChoices[i] = matches[i]
                                }
                                else {
                                    Option("") { Text("Select") }
                                    trades.forEach {
                                        Option(it) {
                                            Text(it)
                                        }
                                    }
                                }
                            }
                        } //Td end
                    } //for end
                } //Tr end
            } //if end
            if (currentRound > 1) {
                val unmatched = matches.mapIndexedNotNull{index, it ->
                    if (it == choices[1][index]){ null }
                    else { it }
                }
                Tr({style{height((boxL * 0.9).px)}}){ //round 2 check
                    for (i in 0..4) {
                        Td({style{width((boxL * 0.9).px)}}){
                            if (matches[i] == choices[1][i]) {
                                Text("✅")
                            }
                            else if (unmatched.contains(choices[1][i])) {
                                Text("🔁")
                            }
                            else {
                                Text("❌")
                            }
                        } //Td end
                    } //for end
                } //Tr end
                Tr({style{height((boxL * 0.9).px)}}){ //round 3
                    for (i in 0..4){
                        Td({style{width((boxL * 0.9).px)}}){
                            Select({
                                style { fontSize((boxL * 0.17).px) }
                                if (currentRound > 2 || matches[i] == choices[1][i]) { disabled() }
                                onChange { savedChoices[i] = it.value!! }
                            }) {
                                if (matches[i] == choices[1][i]) {
                                    Option(choices[1][i]) { Text(choices[1][i]) }
                                    savedChoices[i] = matches[i]
                                }
                                else {
                                    Option("") { Text("Select") }
                                    trades.forEach {
                                        Option(it) {
                                            Text(it)
                                        }
                                    }
                                }
                            }
                        } //Td end
                    } //for end
                } //Tr end
            } //if end
            if (currentRound > 2) {
                val unmatched = matches.mapIndexedNotNull{index, it ->
                    if (it == choices[1][index]){ null }
                    else { it }
                }
                Tr({style{height((boxL * 0.9).px)}}){ //round 3 check
                    for (i in 0..4) {
                        Td({style{width((boxL * 0.9).px)}}){
                            if (matches[i] == choices[2][i]) {
                                Text("✅")
                            }
                            else if (unmatched.contains(choices[2][i])) {
                                Text("🔁")
                            }
                            else {
                                Text("❌")
                            }
                        }
                    } //for end
                } //Tr end
            } //if end
        } //table end

        if (isWin == 5) {
            Span({ style { fontSize((boxL * 0.25).px) } }) { Text("You Won 👍") }
            console.log("if", "round:", currentRound, "points:", isWin)
        }
        else if (currentRound <= 2 && isWin != 5) {
            Button(attrs = {
                style { fontSize((boxL * 0.25).px) }
                if (savedChoices.any { it.isEmpty() } || currentRound > 2) {
                    disabled()
                }
                onClick {
                    for (i in 0..4){
                        if (matches[i] == savedChoices[i]){
                            isWin++
                        }
                    }
                    console.log("onClick 1", "round:", currentRound, "points:", isWin)
                    if (isWin < 5){ isWin = 0 }
                    console.log("onClick", "round:", currentRound, "points:", isWin)
                    currentRound++
                    choices.add(savedChoices)
                    savedChoices = mutableStateListOf("", "", "", "", "")
                    console.log(JSON.stringify(choices))
                }
            }) {
                Text("Confirm choices")
            } //button end
        }
        else {
            Span({ style { fontSize((boxL * 0.25).px) } }) { Text("You Lose 👎") }
            console.log("else", "round:", currentRound, "points:", isWin)
        }
    }
}
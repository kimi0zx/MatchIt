package com.abishek.matchit.models

import com.abishek.matchit.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize: BoardSize){

    val cards: List<MemoryCard>
    var numPairsFound = 0

    private var indexOfSingleSelectedCard: Int? = null
    private var numFlips = 0

    init {
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map{ MemoryCard(it) }
    }

    fun flipCard(position: Int): Boolean {
        numFlips++
        val card = cards[position]
        var foundMatch = false
        if(indexOfSingleSelectedCard == null){
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position) as Boolean
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    private fun checkForMatch(pos1: Int, pos2: Int): Any {
        if(cards[pos1].identifier != cards[pos2].identifier){
            return false
        }
        cards[pos1].isMatched = true
        cards[pos2].isMatched = true
        numPairsFound++
        return true
    }

    private fun restoreCards() {
        for (card in cards){
            if (!card.isMatched){
                card.isFaceUp = false
            }
    }}

    fun haveWon(): Boolean {
        return (numPairsFound == boardSize.getNumPairs()) 
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp
    }

    fun getNumMoves(): Int {
        return numFlips/2
    }

}
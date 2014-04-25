object BowlingScore {
  def apply(frames: String) = {


    def scoreToPins(rolls:String) ={
      //Convert the list of rolls into something uniform for frames
    }


    val pinsList = frames.toList

    def calculateStrike(fl: List[Char]): Int = {
      //Assuming valid game
      val r1 = fl.head
      val nextScore = if (r1.isDigit || r1 == '-') {
        scorePins(r1)
      } else {
        10
      }

      val r2 = fl.tail.head
      val nextNextScore = if (r2.isDigit || r2 == '-') {
        scorePins(r2)
      } else {
        10
      }

      nextScore + nextNextScore
    }

    def calculateSpare(fl: List[Char]): Int = {
      val r1 = fl.head
      if(r1 == 'X') {
        calculateStrike(fl.tail)
      } else {
        scorePins(r1)
      }
    }

    def scorePins(pins: Char): Int = {
      if (pins == '-') {
        0
      } else {
        pins.asDigit
      }
    }

    def calculateScore(frameIndex: Int, score: Int, fl: List[Char], previous: Char): Int = {
      if (fl.isEmpty || frameIndex == 10) {
        score
      } else {
        val pins = fl.head
        //I have a number of pins hit
        // Could be - [1-9] / or X
        if (pins.isDigit || pins == '-') {
          //If we're starting as a number, we'll examine the two in a pair
          val nextPins = fl.tail.head
          val nextScore = if(nextPins == '-' || nextPins.isDigit){
            scorePins(nextPins)
          } else {
            //It's a spare
            calculateSpare(fl.tail)
          }
          //If it's a number, add it to the score and keep going
          //It's also the first part of one frame, so don't increment it
          calculateScore(frameIndex+1, score + scorePins(pins) + nextScore, fl.tail.tail, pins)

        } else if (pins == '/' || pins == 'X') {
          if (pins == '/') {
            //Spare!
            calculateScore(frameIndex + 1, score + scorePins(previous) + calculateStrike(fl.tail), fl.tail, pins)
          } else {
            //Strike!
            calculateScore(frameIndex + 1, score + 10 + calculateStrike(fl.tail), fl.tail, pins)
          }
        } else {
          //TODO: should not need this, need to better scope my crap
          0
        }
      }
    }

    calculateScore(0, 0, pinsList, '-')


    //    frames.toList.foldLeft(0)((a,b) => {
    //      if(b == '-') {
    //        a
    //      } else {
    //        a + b.asDigit
    //      }
    //    })
  }
}
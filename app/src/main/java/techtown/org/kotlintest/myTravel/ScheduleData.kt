package techtown.org.kotlintest.myTravel

data class ScheduleData (
    var scheduleKey: String,
    var day: String,
    var place : String,
    var time : String,
    //val img : Int

){
    constructor(): this("","","", "")


}
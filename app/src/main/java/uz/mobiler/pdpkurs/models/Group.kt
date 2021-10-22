package uz.mobiler.pdpkurs.models

import java.io.Serializable

class Group:Serializable {

    var id:Int?=null
    var name:String?=null
    var course:Course?=null
    var mentor:Mentor?=null
    var time:String?=null
    var days:String?=null
    var isOpened:String?=null

    constructor()

    constructor(
        name: String?,
        course: Course?,
        mentor: Mentor?,
        time: String?,
        days: String?,
        isOpened: String?
    ) {
        this.name = name
        this.course = course
        this.mentor = mentor
        this.time = time
        this.days = days
        this.isOpened = isOpened
    }

    constructor(
        id: Int?,
        name: String?,
        course: Course?,
        mentor: Mentor?,
        time: String?,
        days: String?,
        isOpened: String?
    ) {
        this.id = id
        this.name = name
        this.course = course
        this.mentor = mentor
        this.time = time
        this.days = days
        this.isOpened = isOpened
    }


}
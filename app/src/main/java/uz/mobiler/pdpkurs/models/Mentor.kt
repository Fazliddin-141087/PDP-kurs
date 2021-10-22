package uz.mobiler.pdpkurs.models

import java.io.Serializable

class Mentor:Serializable {

    var id:Int?=null
    var surname:String?=null
    var name:String?=null
    var fatherName:String?=null
    var course:Course?=null

    constructor()


    constructor(surname: String?, name: String?, fatherName: String?, course: Course?) {
        this.surname = surname
        this.name = name
        this.fatherName = fatherName
        this.course = course
    }

    constructor(id: Int?, surname: String?, name: String?, fatherName: String?, course: Course?) {
        this.id = id
        this.surname = surname
        this.name = name
        this.fatherName = fatherName
        this.course = course
    }


}
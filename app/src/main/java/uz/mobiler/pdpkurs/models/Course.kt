package uz.mobiler.pdpkurs.models

import java.io.Serializable

class Course:Serializable {

    var id:Int?=null
    var name:String?=null
    var descreption:String?=null

    constructor()

    constructor(name: String?, descreption: String?) {
        this.name = name
        this.descreption = descreption
    }

    constructor(id: Int?, name: String?, descreption: String?) {
        this.id = id
        this.name = name
        this.descreption = descreption
    }

}
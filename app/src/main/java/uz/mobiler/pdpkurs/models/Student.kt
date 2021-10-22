package uz.mobiler.pdpkurs.models

import java.io.Serializable

class Student:Serializable {

    var id:Int?=null
    var surname:String?=null
    var name:String?=null
    var fatherName:String?=null
    var date: String?=null
    var group: Group?=null

    constructor()

    constructor(
        surname: String?,
        name: String?,
        fatherName: String?,
        date: String?,
        group: Group?
    ) {
        this.surname = surname
        this.name = name
        this.fatherName = fatherName
        this.date = date
        this.group = group
    }

    constructor(
        id: Int?,
        surname: String?,
        name: String?,
        fatherName: String?,
        date: String?,
        group: Group?
    ) {
        this.id = id
        this.surname = surname
        this.name = name
        this.fatherName = fatherName
        this.date = date
        this.group = group
    }

}
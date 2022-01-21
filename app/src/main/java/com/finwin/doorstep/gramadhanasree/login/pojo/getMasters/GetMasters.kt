package com.finwin.doorstep.gramadhanasree.login.pojo.getMasters

 data class GetMasters(
     val ACC_CTG: ACCCTG,
     val ACC_RELAT: ACCRELAT,
     val ACC_TYPE: ACCTYPE,
     val BRANCH_DETAILS: BRANCHDETAILS,
     val CONSTITUTION: CONSTITUTION,
     val GNDR: GNDR,
     val MARSTATUS: MARSTATUS,
     val MODE_OPER: MODEOPER,
     val NTITLE: NTITLE,
     val OCCU: OCCU
)

data class ACCCTG(
    val `data`: List<Data>
)

data class ACCRELAT(
    val `data`: List<Data>
)

data class ACCTYPE(
    val `data`: List<Data>
)

data class BRANCHDETAILS(
    val `data`: List<Data>
)

data class CONSTITUTION(
    val `data`: List<Data>
)

data class GNDR(
    val `data`: List<Data>
)

data class MARSTATUS(
    val `data`: List<Data>
)

data class MODEOPER(
    val `data`: List<Data>
)

data class NTITLE(
    val `data`: List<Data>
)

data class OCCU(
    val `data`: List<Data>
)

data class Data(
    val ID: String,
    val NAME: String,
    val VALUE: String
)

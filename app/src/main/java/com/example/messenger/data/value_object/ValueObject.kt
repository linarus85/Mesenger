package com.example.messenger.data.value_object

data class UserVO(
    val id: Long,
    val username:String,
    val phoneNumber:String,
    val status:String,
    val createAt:String,
)
data class UserListVO(
    val user: List<UserVO>,
)
data class MessageVO(
    val id: Long,
    val senderId: Long,
    val recipientId: Long,
    val conversionId: Long,
    val body:String,
    val createAt:String,
)
data class ConversationVO(
    val conversionId: Long,
    val secandaryUsername:String,
    val message:ArrayList<MessageVO>,
)
data class ConversationListVO(
    val conversations:List<ConversationVO>,
)

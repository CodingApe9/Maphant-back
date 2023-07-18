package com.tovelop.maphant.dto

import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate

data class SignupDTO(
    val email: String,
    val password: String,
    val passwordCheck: String,
    val nickname: String,
    val name: String,
    val sNo: String,
    val phoneNum: String?,
    val universityName: String
) {
    fun toUserDTO(universityId: Int, passwordEncoder: PasswordEncoder): UserDTO {
        return UserDTO(
            email = email,
            password = passwordEncoder.encode(password),
            nickname = nickname,
            name = name,
            phoneNum = null,
            sNo = sNo,
            createAt = LocalDate.now(),
            role = "user",
            state = "0",
            agreedAt = LocalDate.now(),
            lastmodifiedAt = LocalDate.now(),
            univId = universityId
        )
    }
}

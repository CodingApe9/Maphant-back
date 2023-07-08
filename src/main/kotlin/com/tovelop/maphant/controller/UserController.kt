package com.tovelop.maphant.controller

import com.tovelop.maphant.dto.*
import com.tovelop.maphant.service.UserService
import com.tovelop.maphant.utils.Response
import com.tovelop.maphant.utils.ResponseUnit
import com.tovelop.maphant.utils.ValidationHelper
import com.tovelop.maphant.utils.isSuccess
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class SignupController(@Autowired val userService: UserService) {
    //이메일 검증 api
    @PostMapping("/validation/email")
    fun validationEmail(@RequestBody validationSignupDTO: ValidationSignupDTO): ResponseUnit {
        if (!userService.isEmailValid(validationSignupDTO.email!!)) {
            return Response.error(mutableListOf("형식에 맞지 않는 이메일입니다."))
        }

        if (userService.isDuplicateEmail(validationSignupDTO.email)) {
            return Response.error(mutableListOf("이미 사용중인 이메일입니다."))
        }

        return Response.stateOnly(true)
    }

    //nickname 검증 api
    @PostMapping("/validation/nickname")
    fun validationNickname(@RequestBody validationSignupDTO: ValidationSignupDTO): ResponseUnit {
        if (!ValidationHelper.isValidNickname(validationSignupDTO.nickName!!)) {
            return Response.error(mutableListOf("형식에 맞지 않는 별명입니다."))
        }

        if (userService.isDuplicateNickname(validationSignupDTO.nickName)) {
            return Response.error(mutableListOf("이미 사용중인 별명입니다."))
        }

        return Response.stateOnly(true)
    }

    //phoneNum 검증 api
    @PostMapping("/validation/phoneNum")
    fun validationPhonenum(@RequestBody validationSignupDTO: ValidationSignupDTO): ResponseUnit {
        if (!ValidationHelper.isValidPhoneNum(validationSignupDTO.phoneNum!!)) {
            return Response.error(mutableListOf("핸드폰 번호를 형식에 맞춰주세요. ex) 010-1234-5678"))
        }
        if (userService.isDuplicatePhoneNum(validationSignupDTO.phoneNum)) {
            return Response.error(mutableListOf("이미 사용중인 핸드폰 번호입니다."))
        }

        return Response.stateOnly(true)
    }

    //pw 검증 api
    @PostMapping("/validation/password")
    fun validationPassword(@RequestBody validationSignupDTO: ValidationSignupDTO): ResponseUnit {
        if (!ValidationHelper.isValidPassword(validationSignupDTO.password!!)) {
            return Response.error(mutableListOf("비밀번호는 영문 소문자, 대문자, 숫자와 특수문자를 포함하고, 최소 8자로 구성되어야 합니다."))
        }

        return Response.stateOnly(true)
    }

    @PostMapping("/validation/passwordCheck")
    fun validationPasswordChk(@RequestBody validationSignupDTO: ValidationSignupDTO): ResponseUnit {
        if (validationSignupDTO.password != validationSignupDTO.passwordChk) {
            return Response.error(mutableListOf("비밀번호와 비밀번호 확인이 동일하지 않습니다."))
        }

        return Response.stateOnly(true)
    }

    @PostMapping("/signup")
    fun signup(@RequestBody signup: SignupDTO): ResponseUnit {
        val emailValidation = validationEmail(ValidationSignupDTO(email = signup.email))
        if (!emailValidation.isSuccess()) {
            return emailValidation
        }

        val nicknameValidation = validationNickname(ValidationSignupDTO(nickName = signup.nickname))
        if (!nicknameValidation.isSuccess()) {
            return nicknameValidation
        }

        val phoneNumValidation = validationPhonenum(ValidationSignupDTO(phoneNum = signup.phoneNo))
        if (!phoneNumValidation.isSuccess()) {
            return phoneNumValidation
        }

        val passwordValidation = validationPassword(ValidationSignupDTO(password = signup.password))
        if (!passwordValidation.isSuccess()) {
            return passwordValidation
        }

        val passwordChkValidation =
            validationPasswordChk(ValidationSignupDTO(password = signup.password, passwordChk = signup.passwordChk))
        if (!passwordChkValidation.isSuccess()) {
            return passwordChkValidation
        }

        if (!ValidationHelper.isValidName(signup.name)) {
            return Response.error(mutableListOf("이름을 형식에 맞춰주세요. ex) 홍길동"))
        }

        userService.signUp(signup.toUserDTO())
        return Response.stateOnly(true)
    }

    @PostMapping("/login")
    fun login(@RequestBody login: LoginDTO): ResponseUnit {
        //ID, PW DB 체크
        if (true /*입력받은 이메일, 비밀번호 DB에 있는 정보와 동일한 지 확인 */) {
            return Response.error(mutableListOf("이메일, 비밀번호를 확인해주세요."))
        }

        return Response.stateOnly(true)
    }


    @PostMapping("/findemail")
    fun findEmail(@RequestBody findEmail: FindEmailDTO): Response<Map<String, String>> {
        //학번, 전화번호 DB 체크
        val email = "a@ks.ac.kr"

        return Response.success(mutableMapOf("email" to email))
    }

    @PostMapping("/changepw")
    fun ChangePw(@RequestBody changePw: ChangePwDTO): ResponseUnit {
        //이메일 DB 체크
        if (true /*이메일이 일치하지 않을 때*/) {
            return Response.error(mutableListOf("유효하지 않은 이메일입니다."))
        }

        // You might have to handle signService.sendEmail() based on its implementation and return type.

        return Response.stateOnly(true)
        // 여기서 내 이메일을 session을 넘겨줄지, 아니면 data에 email을 넘겨줄지 결정 해야함.
    }

    @PostMapping("/changepw/authenticationcode")
    fun authenticationCode(@RequestBody newPw: NewPwDTO) {
        //인증번호 확인
        // You might have to handle this method based on its implementation and return type.
    }

    @PostMapping("/newpw")
    fun newPw(@RequestBody newPw: NewPwDTO): ResponseUnit {
        //패스워드 입력, 검증
        // You might have to handle userService related function based on its implementation and return type.

        //DB 패스워드 치환
        return Response.stateOnly(true)
    }
}

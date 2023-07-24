package com.tovelop.maphant.mapper

import com.tovelop.maphant.dto.BoardDTO
import com.tovelop.maphant.dto.ProfileImageDto
import com.tovelop.maphant.dto.UploadLogDTO
import com.tovelop.maphant.type.response.SuccessResponse
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile

@Mapper
@Repository
interface ProfileMapper {
    // user테이블의 id 값으로 프로필 사진 불러오기
    fun findImageById(userId:Int):ProfileImageDto
    
    // 프로필 사진 바꾸기
    // 프로필 테이블에 있는
    fun updateProfileImage(userId: Int, imageUrl:String):Boolean

    //회원의 모든 작성글 목록 불러오기(board로 옮겨야함)
    fun findAllPostsById(userId:Int): List<BoardDTO>

    //회원의 모든 작성 댓글 목록 불러오기(comment로 옮겨야함)
    fun findAllCommentsById(userId: Int)
}
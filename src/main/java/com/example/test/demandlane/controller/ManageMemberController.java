package com.example.test.demandlane.controller;

import com.example.test.demandlane.model.dto.request.RequestMember;
import com.example.test.demandlane.model.dto.response.ApiResponse;
import com.example.test.demandlane.model.entity.Member;
import com.example.test.demandlane.service.ManageMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demandlane/member")
@RequiredArgsConstructor
public class ManageMemberController {

    private final ManageMemberService manageMemberService;

    @PostMapping("/manage/addMember")
    public ResponseEntity<ApiResponse<Member>> addMember(@Valid @RequestBody RequestMember member) {
        Member addMember = manageMemberService.addMember(member);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Member added successfully", addMember));
    }

    @GetMapping("/getMember/{id}")
    public ResponseEntity<ApiResponse<Member>> getMemberById(@PathVariable Long id) {
        Member member = manageMemberService.getMemberById(id);
        return ResponseEntity.ok(ApiResponse.success("Member found", member));
    }

    @GetMapping("/getMembers")
    public ResponseEntity<ApiResponse<List<Member>>> getAllMembers() {
        List<Member> members = manageMemberService.getAllMember();
        return ResponseEntity.ok(ApiResponse.success("Success get all members", members));
    }

    @PatchMapping("/manage/updateMember/{id}")
    public ResponseEntity<ApiResponse<Member>> updateMember(@PathVariable Long id, @Valid @RequestBody RequestMember member) {
        Member updateMember = manageMemberService.updateMember(id, member);
        return ResponseEntity.ok(ApiResponse.success("Member updated successfully", updateMember));
    }

    @DeleteMapping("/manage/deleteMember/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        manageMemberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}

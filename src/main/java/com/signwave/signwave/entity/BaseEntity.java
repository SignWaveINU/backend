package com.signwave.signwave.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
//AuditingEntityListener가 엔티티가 저장될 때(@CreationTimestamp), 수정될 때(@UpdateTimestamp)를 감지해서 알아서 생성시간, 수정시간의 현재시간을 자동기록 해줌
//@EntityListeners → "이 엔티티를 누가 감시하게 할 건지 지정
// (AuditingEntityListener.class) → "시간 같은 걸 자동으로 관리해주는 감시자
@Getter
public class BaseEntity {
    @CreationTimestamp //객체가 저장될 때 createdTime 필드에 자동으로 현재 시간을 넣어줌
    @Column(updatable = false) //생성(INSERT)은 가능하지만 수정(UPDATE)은 불가능하게 하는 설정
    //reatedTime은 처음 생성될 때 한 번만 기록되고, 수정할 때는 변경 안 됨
    private LocalDateTime createdTime;

    @UpdateTimestamp //객체가 수정될 때(UPDATE) updatedTime 필드에 자동으로 현재 시간을 넣어줌
    @Column(insertable = false) //저장(INSERT)할 때는 무시하고 수정(UPDATE)할 때만 값을 채움
    //updatedTime은 처음 저장할 때는 굳이 필요 없고, 수정할 때 기록하면 됨
    private LocalDateTime updatedTime;
}

package org.delivery.db.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;

@SuperBuilder // 부모가 가지고 있는 변수도 지정할 수 있게 하려고 @Builder 대신 사용
@Data
@EqualsAndHashCode(callSuper = true) // 객체 비교 시 부모에 있는 값까지 함께 비교
@Entity
@Table(name = "account") // DB의 account table의 엔티티다
public class AccountEntity extends BaseEntity {
}

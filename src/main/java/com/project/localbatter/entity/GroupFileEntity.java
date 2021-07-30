package com.project.localbatter.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_group_file")
public class GroupFileEntity extends BaseTimeEntity implements Serializable{

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @Column(name = "group_path")
    private String path;

    @Column(name = "file_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private GroupEntity group;

}

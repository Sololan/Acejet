package com.wejoy.wejoycms.entity.dto;

import com.wejoy.wejoycms.entity.TSubject;
import com.wejoy.wejoycms.entity.TSubjectPicture;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectDto extends TSubject {
    private List<TSubjectPicture> pictures;
}

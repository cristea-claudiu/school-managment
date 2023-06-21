package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concrate.Meet;
import com.project.schoolmanagment.repository.MeetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MeetService {
    private final MeetRepository meetRepository;
    public List<Meet> getAllMeetsById(List<Long> meetListId) {
        return meetRepository.findAllById(meetListId);
    }
}

package com.project_management.infrastructure.service;

import com.project_management.application.dto.sprint.SprintDto;
import com.project_management.application.mapper.SprintMapper;
import com.project_management.application.repository.ProjectRepository;
import com.project_management.application.repository.SprintRepository;
import com.project_management.application.repository.UserRepository;
import com.project_management.application.service.SprintService;
import com.project_management.domain.model.Project;
import com.project_management.domain.model.Sprint;
import com.project_management.domain.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    @Autowired
    private final SprintMapper sprintMapper;

    @Override
    public SprintDto createSprint(SprintDto sprintDto) {
        Project project = projectRepository.findById(sprintDto.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        Sprint sprint = sprintMapper.toEntity(sprintDto);
        sprint.setProject(project);
        User user = userRepository.findByEmail(sprintDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        sprint.setCreator(user);
        Sprint savedSprint = sprintRepository.save(sprint);
        return sprintMapper.toDto(savedSprint);
    }

    @Override
    public List<SprintDto> getSprintsByProject(Long projectId) {
        List<Sprint> sprints = sprintRepository.findByProjectId(projectId);
        return sprints.stream().map(sprintMapper::toDto).collect(Collectors.toList());
    }
}
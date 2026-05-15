package org.example.crm_project.modules.opportunity_management.presentation.controller;

import org.example.crm_project.modules.opportunity_management.application.dto.StageChecklistRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.StageChecklistResponse;
import org.example.crm_project.modules.opportunity_management.application.service.PipelineStageService;
import org.example.crm_project.modules.opportunity_management.application.service.StageChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

/**
 * Presentation Controller – StageChecklist.
 */
@Controller
@RequestMapping("/crm/checklists")
@RequiredArgsConstructor
public class StageChecklistController {

    private final StageChecklistService checklistService;
    private final PipelineStageService stageService;

    @GetMapping
    public String getAll(Model model) {
        List<StageChecklistResponse> list = checklistService.getAll();
        list.sort(Comparator.comparing(
                sc -> sc.getStageName() != null ? sc.getStageName() : ""));

        model.addAttribute("listData", list);
        model.addAttribute("listStages", stageService.getAll());
        model.addAttribute("newData", new StageChecklistRequest());
        return "stage-checklists";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("newData") StageChecklistRequest request) {
        if (request.getIsMandatory() == null) request.setIsMandatory(false);
        if (request.getId() != null) {
            checklistService.update(request.getId(), request);
        } else {
            checklistService.create(request);
        }
        return "redirect:/crm/checklists";
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public StageChecklistResponse getApi(@PathVariable Integer id) {
        return checklistService.getById(id);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        checklistService.delete(id);
        return "redirect:/crm/checklists";
    }
}

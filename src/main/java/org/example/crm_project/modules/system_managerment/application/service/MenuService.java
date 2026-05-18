package org.example.crm_project.modules.system_managerment.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.application.dto.request.CreateMenuRequest;
import org.example.crm_project.modules.system_managerment.application.dto.request.UpdateMenuRequest;
import org.example.crm_project.modules.system_managerment.application.dto.response.MenuResponse;
import org.example.crm_project.modules.system_managerment.application.mapper.MenuMapper;
import org.example.crm_project.modules.system_managerment.domain.entity.Menu;
import org.example.crm_project.modules.system_managerment.domain.exception.MenuNotFoundException;
import org.example.crm_project.modules.system_managerment.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;

    // ===== CREATE =====
    public MenuResponse create(CreateMenuRequest req) {

        // check parent tồn tại
        if (req.getParentId() != null && !repository.existsById(req.getParentId())) {
            throw new RuntimeException("Parent menu not found");
        }

        Menu menu = MenuMapper.toEntity(req);

        return MenuMapper.toResponse(repository.save(menu));
    }

    // ===== UPDATE (có cycle check) =====
    public MenuResponse update(Long id, UpdateMenuRequest req) {

        Menu menu = repository.findById(id)
                .orElseThrow(() -> new MenuNotFoundException(id));

        // validate parent tồn tại
        if (req.getParentId() != null && !repository.existsById(req.getParentId())) {
            throw new RuntimeException("Parent menu not found");
        }

        // 🔥 CHECK CYCLE
        if (req.getParentId() != null) {
            validateNoCycle(id, req.getParentId());
        }

        if (req.getName() != null) {
            menu.changeName(req.getName());
        }

        menu.changeParent(req.getParentId());

        return MenuMapper.toResponse(repository.save(menu));
    }

    // ===== DELETE =====
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new MenuNotFoundException(id);
        }
        repository.deleteById(id);
    }

    // ===== GET ALL =====
    public List<MenuResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(MenuMapper::toResponse)
                .toList();
    }

    // ===== TREE =====
    public List<MenuResponse> getTree() {

        List<Menu> all = repository.findAll();

        Map<Long, MenuResponse> map = new HashMap<>();

        for (Menu m : all) {
            map.put(m.getId(), MenuMapper.toResponse(m));
        }

        List<MenuResponse> roots = new ArrayList<>();

        for (Menu m : all) {
            MenuResponse node = map.get(m.getId());

            if (m.getParentId() == null) {
                roots.add(node);
            } else {
                MenuResponse parent = map.get(m.getParentId());

                if (parent != null) {
                    parent.getChildren().add(node);
                } else {
                    roots.add(node);
                }
            }
        }

        return roots;
    }

    // =========================
    // 🔥 CYCLE DETECTION (DFS)
    // =========================
    private void validateNoCycle(Long nodeId, Long newParentId) {

        if (nodeId.equals(newParentId)) {
            throw new RuntimeException("Cannot set itself as parent");
        }

        Long current = newParentId;

        while (current != null) {

            if (current.equals(nodeId)) {
                throw new RuntimeException("Cycle detected in menu tree");
            }

            Menu parent = repository.findById(current).orElse(null);

            if (parent == null) break;

            current = parent.getParentId();
        }
    }
}
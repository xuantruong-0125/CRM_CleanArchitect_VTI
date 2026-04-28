package org.example.crm_project.modules.products_managerment.application.interfaces;

import java.util.List;

public interface BaseService<REQ_CREATE, REQ_UPDATE, RES, ID> {
    RES create(REQ_CREATE req);
    RES update(ID id, REQ_UPDATE req);
    void delete(ID id);
    void deleteByIds(List<ID> ids);
    RES getById(ID id);
    List<RES> getAll();
}

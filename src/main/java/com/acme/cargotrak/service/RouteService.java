package com.acme.cargotrak.service;

import java.util.List;

import com.acme.cargotrak.dao.RouteDao;
import com.acme.cargotrak.domain.Route;

public class RouteService {

    private RouteDao routeDao;
    public void setRouteDao(RouteDao r) { this.routeDao = r; }

    public List listAll() { return routeDao.findAll(Route.class); }
    public Route findById(Integer id) { return (Route) routeDao.findById(Route.class, id); }
    public Route findByCode(String code) { return routeDao.findByCode(code); }
    public Integer create(Route r) { return (Integer) routeDao.save(r); }
    public void update(Route r) { routeDao.update(r); }
}

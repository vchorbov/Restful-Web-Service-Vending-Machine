package chorbova.velichka.restful.web.service.service;

import chorbova.velichka.restful.web.service.repository.BeverageRepository;
import chorbova.velichka.restful.web.service.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemServiceImpl extends MachineService implements ItemService {

    @Autowired
    BeverageRepository beverageRepository;

    @Autowired
    FoodRepository foodRepository;
}

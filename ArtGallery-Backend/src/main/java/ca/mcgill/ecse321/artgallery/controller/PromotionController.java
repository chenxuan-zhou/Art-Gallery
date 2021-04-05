package ca.mcgill.ecse321.artgallery.controller;
import ca.mcgill.ecse321.artgallery.dao.PromotionRepository;
import ca.mcgill.ecse321.artgallery.dto.AccountDto;
import ca.mcgill.ecse321.artgallery.dto.ManagerDto;
import ca.mcgill.ecse321.artgallery.dto.ProductDto;
import ca.mcgill.ecse321.artgallery.dto.PromotionDto;
import ca.mcgill.ecse321.artgallery.model.*;
import ca.mcgill.ecse321.artgallery.service.ManagerService;
import ca.mcgill.ecse321.artgallery.service.ProductService;
import ca.mcgill.ecse321.artgallery.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321.artgallery.dao.ProductRepository;

import java.sql.Date;
import ca.mcgill.ecse321.artgallery.model.Promotion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PromotionController responds to http response
 * @author Yutian Fu
 */
@CrossOrigin(origins = "*")
@RestController
public class PromotionController {
    @Autowired
    private PromotionService promotionservice;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;


    @GetMapping(value = { "/promotions/" })
    public List<PromotionDto> getAllPromotions() {
        return promotionservice.getAllPromotions().stream().map(promotion -> convertToDto(promotion)).collect(Collectors.toList());
    }

    /**
     * http://localhost:8080/promotion/create?manager=1234567@mail&id=1&start=2020-09-01&end=2020-10-01&product=1
     * @param manager_email
     * @param id
     * @param start
     * @param end
     * @param product_id
     * @return
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/promotion/create", "/promotion/create/" })
    public PromotionDto createPromotion(@RequestParam("manager") String manager_email,@RequestParam("id") Long id,@RequestParam("start") String start,@RequestParam("end") String end,@RequestParam("product") Long product_id) throws IllegalArgumentException{

        Date start_date=Date.valueOf(start);
        Date end_date=Date.valueOf(end);
        Promotion promotion=promotionservice.createPromotion(managerService.findManager(manager_email),id,start_date,end_date,productRepository.findById(product_id).get());
        return convertToDto(promotion);
    }

    /**
     * http://localhost:8080/promotion/id?id=1
     * @param id
     * @return
     */
    @GetMapping(value = {"/promotion/id"})
    public PromotionDto getPromotion(@RequestParam("id") Long id)
    {

        return convertToDto(promotionservice.getPromotion(id));
    }

    /**
     * http://localhost:8080/promotion/start_date?date=2020-09-01
     * @param date
     * @return
     */
    @GetMapping( value = {"/promotion/start_date","/promotion/start_date/"})
    public List<PromotionDto> getPostsByDate(@RequestParam("date") String date)
    {
        return promotionservice.getPromotionByStartDate(Date.valueOf(date)).stream().map(promotion -> convertToDto(promotion)).collect(Collectors.toList());
    }

    /**
     * http://localhost:8080/promotion/end_date?date=2020-10-01
     * @param date
     * @return
     */
    @GetMapping( value = {"/promotion/end_date","/promotion/end_date/"})
    public List<PromotionDto> getPostsByEDate(@RequestParam("date") String date)
    {
        return promotionservice.getPromotionByEndDate(Date.valueOf(date)).stream().map(promotion -> convertToDto(promotion)).collect(Collectors.toList());
    }

    /**
     * http://localhost:8080/delete-promotion?id=1
     * @param id
     * @return
     */
    @DeleteMapping( value = {"/delete-promotion"})
    public List<PromotionDto> deletePromotion(@RequestParam("id") Long id)
    {


        List<PromotionDto> newpromotion=promotionservice.delete(id).stream().map(promotion -> convertToDto(promotion)).collect(Collectors.toList());;
        return newpromotion;
    }


    private PromotionDto convertToDto(Promotion promt) {
        if (promt == null) {
            throw new IllegalArgumentException("There is no such Promotion");
        }


//        List<Product> list=promt.getProduct();
//
//        List<ProductDto> productDto=new ArrayList<>();
//        for(Product a:list){
//            ProductDto dto=convertToDtoP(a);
//            productDto.add(dto);
//        }
        //List<ProductDto> productDto=ProductController.convertToDto(promt.getProduct());
        List<Product> list=promt.getProduct();
        List<Long> products=new ArrayList<>();
        for(Product a:list){
            products.add(a.getId());
        }

        ManagerDto managerDto=convertToDtoM(promt.getManager());
        PromotionDto promotionDto = new PromotionDto(promt.getId(),promt.getStartDate(),promt.getEndDate(),products,managerDto);

        return promotionDto;
    }

    private ProductDto convertToDtoP(Product aProduct) {
        if (aProduct == null) {
            throw new IllegalArgumentException("There is no such Product!");
        }
        ProductDto productDto = new ProductDto(ProductController.convertStatus(aProduct.getProductStatus()),ProductController.convertOption(aProduct.getSupportedDelivery()),aProduct.getName(),aProduct.getPrice(),aProduct.getSeller().getEmail(),null,null,"");
        productDto.setId(aProduct.getId());


        return productDto;
    }

    private ManagerDto convertToDtoM(Manager aManager) {
        if (aManager == null) {
            throw new IllegalArgumentException("There is no such Manager!");
        }
        ManagerDto managerDto = new ManagerDto(aManager.getEmail(),aManager.getName(),aManager.getPassword(),convertStatus(aManager.getStatus()));
        return managerDto;
    }

    static AccountDto.AccountStatus convertStatus(Account.AccountStatus status){
        switch(status) {
            case New : return AccountDto.AccountStatus.New;
            case Blocked: return AccountDto.AccountStatus.Blocked;
            case Active: return AccountDto.AccountStatus.Active;
            case Inactive: return AccountDto.AccountStatus.Inactive;
            default: throw new IllegalArgumentException();
        }
    }




}

package ca.mcgill.ecse321.artgallery.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.artgallery.dao.ManagerRepository;
import ca.mcgill.ecse321.artgallery.dao.ProductRepository;
import ca.mcgill.ecse321.artgallery.dao.PromotionRepository;
import ca.mcgill.ecse321.artgallery.model.Account;
import ca.mcgill.ecse321.artgallery.model.Manager;
import ca.mcgill.ecse321.artgallery.model.Product;
import ca.mcgill.ecse321.artgallery.model.Promotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

/**
 * PromotionServiceTest is used to test promotion service
 * @author Yutian Fu
 */
@ExtendWith(MockitoExtension.class)
public class TestPromotionService {
    @InjectMocks
    private PromotionService promotionService;

    @Mock
    private PromotionRepository promotionRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ManagerRepository managerRepository;


    private static final long promotId = 123;
    private static final String start = "2020-08-01";
    private static final String end="2020-09-01";
    private static final  Date  promotStart= Date.valueOf(start);
    private static final  Date  promotEnd= Date.valueOf(end);

    private static final long promotId2=234;
    private static final String start2 = "2020-010-01";
    private static final String end2="2020-011-01";
    private static final  Date  promotStart2= Date.valueOf(start);
    private static final  Date  promotEnd2= Date.valueOf(end);


    private static final String managerEmail="test1@manager.com";
    private static final String managerName="ManagerA";
    private static final String managerPassword="123";
    private static final Account.AccountStatus managerStatus= Account.AccountStatus.Active;
    private static final Manager testManager=createManager(managerEmail,managerName,managerStatus,managerPassword);

    private static final String manager2Email="test2@manager.com";
    private static final String manager2Name="ManagerB";
    private static final String manager2Password="1234";
    private static final Manager testManager2=createManager(manager2Email,manager2Name,managerStatus,manager2Password);


    private static final  Product.ProductStatus  status= Product.ProductStatus.Selling;
    private static final int price=1;
    private static final String name="ProductA";
    private static final long product_id=123;
    private static final Product testProduct=createProduct(price,name,status,product_id);

    private static final int price2=2;
    private static final String name2="ProductB";
    private static final long product_id2=1234;
    private static final Product testProduct2=createProduct(price2,name2,status,product_id2);



    @BeforeEach
    public void setMockOutput() {
        lenient().when(managerRepository.findManagerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(managerEmail)) {

                return testManager;
            }else{
                return null;
            }
        });
        lenient().when(productRepository.findProductById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(product_id)) {
                return testProduct;
            }else{
                return null;
            }
        });
        lenient().when(promotionRepository.findPromotionById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(promotId)) {
                Promotion promotion = createPromotion(promotId,promotStart,promotEnd);
                promotion.setManager(testManager);
                List<Product> list=new ArrayList<>();
                list.add(testProduct);
                promotion.setProduct(list);
                return promotion;

            } else {
                return null;
            }
        });

        lenient().when(promotionRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {

            List<Promotion> list= new ArrayList<>();
            Promotion promotion1 = createPromotion(promotId,promotStart,promotEnd);
            Promotion promotion2=createPromotion(promotId2,promotStart2,promotEnd2);
            list.add(promotion1);
            list.add(promotion2);
            return list;

        });
        lenient().when(promotionRepository.findPromotionByEndDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(promotEnd)) {
                List<Promotion> promotion =new ArrayList<>();
                Promotion promotion1 = createPromotion(promotId,promotStart,promotEnd);
                Promotion promotion2=createPromotion(promotId2,promotStart2,promotEnd);
                List<Product> list1=new ArrayList<>();
                list1.add(testProduct);
                promotion1.setProduct(list1);
                promotion1.setManager(testManager);

                List<Product> list2=new ArrayList<>();
                list2.add(testProduct2);
                promotion2.setProduct(list2);
                promotion2.setManager(testManager2);

                promotion.add(promotion1);
                promotion.add(promotion2);

                return promotion;
            }else{
                return null;
            }
        });
        lenient().when(promotionRepository.findPromotionByStartDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(promotStart)) {
                List<Promotion> promotion =new ArrayList<>();
                Promotion promotion1 = createPromotion(promotId,promotStart,promotEnd);
                Promotion promotion2=createPromotion(promotId2,promotStart,promotEnd2);
                List<Product> list1=new ArrayList<>();
                list1.add(testProduct);
                promotion1.setProduct(list1);
                promotion1.setManager(testManager);

                List<Product> list2=new ArrayList<>();
                list2.add(testProduct2);
                promotion2.setProduct(list2);
                promotion2.setManager(testManager2);

                promotion.add(promotion1);
                promotion.add(promotion2);

                return promotion;
            }else{
                return null;
            }
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(promotionRepository.save(any(Promotion.class))).thenAnswer(returnParameterAsAnswer);
        //lenient().when(managerRepository.save(any(Manager.class))).thenAnswer(returnParameterAsAnswer);

    }

    @Test
    public void testCreatePromotion() {
        //Product
        Promotion newpromt=promotionService.createPromotion(testManager,promotId,promotStart,promotEnd,testProduct);
        assertNotNull(newpromt);
        assertEquals(promotId,newpromt.getId());
        assertEquals(promotStart,newpromt.getStartDate());
        assertEquals(promotEnd,newpromt.getEndDate());
        assertEquals(product_id,newpromt.getProduct().get(0).getId());
        assertEquals(managerEmail,newpromt.getManager().getEmail());
    }
    @Test
    public void testCreateWithoutManager(){
        String error = null;
        try
        {
            promotionService.createPromotion(null,promotId,promotStart,promotEnd,testProduct);
        }
        catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertEquals("Please log in to your account",error);


    }
    @Test
    public void testCreateWithoutProduct(){
        String error = null;
        try
        {
            promotionService.createPromotion(testManager,promotId,promotStart,promotEnd,null);
        }
        catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertEquals("Please add at least one product",error);


    }
    @Test
    public void testCreateInvalidAccountStatus(){
        String error = null;
        try
        {
            testManager2.setStatus(Account.AccountStatus.Blocked);
            promotionService.createPromotion(testManager2,promotId,promotStart,promotEnd,testProduct);
        }
        catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertEquals("Your account is blocked and you cannot do this action",error);


    }
    @Test
    public void testGetPromotionById(){
        Promotion promotion=promotionService.getPromotion(promotId);
        assertEquals(managerEmail,promotion.getManager().getEmail());
        assertEquals(promotId,promotion.getProduct().get(0).getId());


    }

    @Test
    public void testGetAllPromotion(){
        List<Promotion> promotions=promotionService.getAllPromotions();
        assertEquals(promotId,promotions.get(0).getId());
        assertEquals(promotId2,promotions.get(1).getId());


    }
    @Test
    public void testGetPromotionByEndDate(){
        List<Promotion> promotion=promotionService.getPromotionByEndDate(promotEnd);
        assertEquals(2,promotion.size());
        assertEquals(promotId,promotion.get(0).getId());
        assertEquals(promotId2,promotion.get(1).getId());



    }
    @Test
    public void testGetPromotionByStartDate(){
        List<Promotion> promotion=promotionService.getPromotionByStartDate(promotStart);
        assertEquals(2,promotion.size());
        assertEquals(promotId,promotion.get(0).getId());
        assertEquals(promotId2,promotion.get(1).getId());

    }
    @Test
    public void testExtendPromotionDate(){
        Promotion promt=promotionService.extendPromotionDate(promotId,promotEnd2);
        assertEquals(promotEnd2,promt.getEndDate());
    }

    private static Manager createManager(String managerEmail1, String managerName1, Account.AccountStatus managerStatus1,String managerPassword1 ){
        Manager manager = new Manager();
        manager.setEmail(managerEmail1);
        manager.setStatus(managerStatus1);
        manager.setName(managerName1);
        manager.setPassword(managerPassword1);
        return manager;

    }
    private static Product createProduct(int price1, String name1, Product.ProductStatus status1,long id1){

        Product product = new Product();
        product.setPrice(price1);
        product.setName(name1);
        product.setProductStatus(status1);
        product.setId(id1);
        return product;
    }
    private static Promotion createPromotion(Long id,Date start,Date end ){
        Promotion promotion =new Promotion();
        promotion.setId(id);
        promotion.setStartDate(start);
        promotion.setEndDate(end);
        return promotion;
    }

}

package com.shoesshop.controllers;

import com.shoesshop.models.Account;
import com.shoesshop.models.Category;
import com.shoesshop.models.Product;
import com.shoesshop.services.AccountService;
import com.shoesshop.services.CategoryService;
import com.shoesshop.services.ProductService;
import com.shoesshop.utils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.shoesshop.utils.Time.getPresentTime;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AccountService accountService;


    @GetMapping("")
    public String getIndex(@CookieValue(name = "email", defaultValue = "") String email){
        if(!email.equals("")){
            Optional<Account> accountSaved = accountService.getAccountByEmail(email);
            if(accountSaved.get().getRole().equals("ROLE_ADMIN")){
                return "admin/index";
            }
        }

        return "redirect:/";
    }

    @GetMapping("user")
    public String getUser(Model model){
        List<Account> accountList = accountService.getAccountList();
        model.addAttribute("accountList", accountList);
        return "admin/user";
    }
    @PostMapping("user/{id}")
    public String postUser(@PathVariable Long id,
                           @RequestParam(name = "role") String role,
                           @RequestParam(name = "status") Boolean status){
        Account account = accountService.getAccountById(id).get();
        account.setRole(role);
        account.setActive(status);
        account.setUpdateAt(getPresentTime());

        accountService.saveAccount(account);
        return "redirect:/admin/user";
    }
    @GetMapping("user/search")
    public String searchUser(@RequestParam(name = "keyword") String keyword, Model model){
        List<Account> accounts = accountService.search(keyword);
        model.addAttribute("accountList", accounts);
        model.addAttribute("keyword", keyword);
        return "admin/user";
    }
    @GetMapping("category")
    public String getCategory(Model model){
        List<Category> categoryList = categoryService.getAllList();
        model.addAttribute("categories", categoryList);
        return "admin/category";
    }
    @PostMapping("category")
    public String postCategory(@ModelAttribute("category") Category category){
        categoryService.save(category);
        return "redirect:/admin/category";
    }
    @PostMapping("category/edit/{id}")
    public String editCategory(@PathVariable Long id, @RequestParam(name = "name") String name){
        Category category = categoryService.getById(id).get();
        category.setName(name);
        category.setUpdatedAt(getPresentTime());
        categoryService.save(category);
        return "redirect:/admin/category";
    }
    @PostMapping("category/delete/{id}")
    public String deleteCategory(@PathVariable Long id){
        categoryService.delete(id);
        return "redirect:/admin/category";
    }
    @GetMapping("product")
    public String getProducts(){
        return "redirect:/admin/product/0?sortField=name&sortDir=asc";
    }

    @GetMapping("product/{pageNum}")
    public String getProductList(@PathVariable(name = "pageNum") int pageNum,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword,
                             Model model){
        Page<Product> products = productService.pageProductsandSort(pageNum,sortField,sortDir,keyword);
        List<Category> categoryList = categoryService.getAllList();
        model.addAttribute("categories", categoryList);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("size", products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("productsList", products);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        return "admin/product";
    }
    @GetMapping("product/add")
    public String getAddProduct(Model model){
        List<Category> categoryList = categoryService.getAllList();
        model.addAttribute("categories", categoryList);
        return "admin/add-product";
    }

    @PostMapping("product/add")
    public String postAddProduct(@ModelAttribute("product") Product product,
                                 @RequestParam("image1")  MultipartFile multipartFile1,
                                 @RequestParam("image2")  MultipartFile multipartFile2,
                                 @RequestParam("image3") MultipartFile multipartFile3,
                                 Model model
                                 ) throws IOException {
        String filename1 = StringUtils.cleanPath(Objects.requireNonNull(multipartFile1.getOriginalFilename()));
        String filename2 = StringUtils.cleanPath(Objects.requireNonNull(multipartFile2.getOriginalFilename()));
        String filename3 = StringUtils.cleanPath(Objects.requireNonNull(multipartFile3.getOriginalFilename()));
        if(!filename1.isEmpty()){
            product.setFirstImage(filename1);
            UploadFile.uploadFiles(multipartFile1, filename1);

        }else{
            product.setFirstImage(null);
        }

        if(!filename2.isEmpty()){
            product.setSecondImage(filename2);
            UploadFile.uploadFiles(multipartFile2, filename2);

        }else{
            product.setSecondImage(null);
        }

        if(!filename1.isEmpty()){
            product.setThirdImage(filename3);
            UploadFile.uploadFiles(multipartFile1, filename3);
        }else{
            product.setThirdImage(null);
        }
        productService.save(product);

        List<Category> categories = categoryService.getAllList();
        model.addAttribute("categories", categories);
        model.addAttribute("message","Thêm sản phẩm thành công");
        return "admin/add-product";
    }
    @PostMapping("product/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @ModelAttribute("product") Product product,
                              @RequestParam("image1")  MultipartFile multipartFile1,
                              @RequestParam("image2")  MultipartFile multipartFile2,
                              @RequestParam("image3") MultipartFile multipartFile3,
                              Model model
    ){
        try{
            Optional<Product> productSaved = productService.getById(id);

            String filename1 = StringUtils.cleanPath(Objects.requireNonNull(multipartFile1.getOriginalFilename()));
            String filename2 = StringUtils.cleanPath(Objects.requireNonNull(multipartFile2.getOriginalFilename()));
            String filename3 = StringUtils.cleanPath(Objects.requireNonNull(multipartFile3.getOriginalFilename()));

            if(!filename1.isEmpty()){
                product.setFirstImage(filename1);
                UploadFile.uploadFiles(multipartFile1, filename1);

            }else{
                product.setFirstImage(productSaved.get().getFirstImage());
            }

            if(!filename2.isEmpty()){
                product.setSecondImage(filename2);
                UploadFile.uploadFiles(multipartFile2, filename2);

            }else{
                product.setSecondImage(productSaved.get().getSecondImage());
            }

            if(!filename3.isEmpty()){
                product.setThirdImage(filename3);
                UploadFile.uploadFiles(multipartFile3, filename3);
            }else{
                product.setThirdImage(productSaved.get().getThirdImage());
            }
        }catch (IOException e){

        }
        productService.save(product);
        model.addAttribute("message", "Chỉnh sửa sản phẩm thành công");

        return "redirect:/admin/product";
    }
    @PostMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        Optional<Product> product = productService.getById(id);
        if(product.isPresent()){
            product.get().setCategory(null);
            productService.delete(product.get());
        }

        return "redirect:/admin/product";
    }
    @GetMapping("product/search")
    public String getSearchKeyWord(Model model,
                                   @RequestParam(name = "keyword") String keyword){
        List<Product> products = productService.searchProductByKeyword(keyword);
        model.addAttribute("productsList", products);
        model.addAttribute("keyword", keyword);
        return "admin/product";
    }

}

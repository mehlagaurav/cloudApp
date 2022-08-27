package reverb.rdscrudservice.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reverb.rdscrudservice.Modal.Book;
import reverb.rdscrudservice.repository.RDSRepository;

import javax.el.ELException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private RDSRepository rdsRepository;
    @PostMapping("/add")
    public Book saveBook(@RequestBody Book book){
        return rdsRepository.save(book);
    }
    @GetMapping("/all")
    public List<Book> getBooks(){
        return rdsRepository.findAll();
    }
    @SneakyThrows
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id){
        Book book= rdsRepository.findById(id).orElseThrow(()->new Exception("book not found"));
        return book;
    }


}

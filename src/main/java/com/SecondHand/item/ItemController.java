package com.SecondHand.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private S3Service s3Service;

    @GetMapping("/item")
    public String showWriteForm(Model model) {
        model.addAttribute("item", new Item());
        return "item";
    }

    @PostMapping("/item")
    public String submitItem(@ModelAttribute Item item, @RequestParam("imgFile") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            try {

                String imgURL = s3Service.uploadFile(file);
                item.setImgURL(imgURL);
            } catch (RuntimeException e) {
                throw new RuntimeException("파일 업로드 중 오류 발생: " + e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("파일이 비어있습니다.");
        }

        itemService.saveItem(item);
        return "redirect:/item/list";
    }

    @GetMapping("/item/list")
    public String listItems(Model model) {
        List<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "itemList";
    }

    @PostMapping("/item/delete")
    public String deleteItem(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Item item = itemService.getItemById(id); // 아이템 조회
        if (item != null) {
            try {
                // S3에서 파일 삭제
                String fileName = item.getImgURL().substring(item.getImgURL().lastIndexOf("/") + 1); // 파일 이름 추출
                s3Service.deleteFile(fileName); // S3에서 파일 삭제
                itemService.deleteItem(id); // 데이터베이스에서 아이템 삭제

                // 성공 메시지 추가
                redirectAttributes.addFlashAttribute("message", "아이템이 성공적으로 삭제되었습니다.");
            } catch (Exception e) {
                // 오류 메시지 추가
                redirectAttributes.addFlashAttribute("error", "아이템 삭제 중 오류가 발생했습니다.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "아이템을 찾을 수 없습니다.");
        }
        return "redirect:/item/list";
    }
}

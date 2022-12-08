package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import peaksoft.model.Groups;
import peaksoft.model.Student;
import peaksoft.service.GroupsService;
import peaksoft.service.StudentService;

@Controller
public class GroupsController {

    private final GroupsService groupService;
    private final StudentService studentService;

    @Autowired
    public GroupsController(GroupsService groupService, StudentService studentService) {
        this.groupService = groupService;
        this.studentService = studentService;
    }

    @GetMapping("/groups/{companyId}/{id}")
    public String getAllGroups(@PathVariable("id") Long id, @PathVariable("companyId")
    Long companyId, Model model, @ModelAttribute("student") Student student) {
        model.addAttribute("groupCourses", groupService.getAllGroupsByCourseId(id));
        model.addAttribute("students", studentService.listAllStudents());
        model.addAttribute("courseId", id);
        model.addAttribute("companyId", companyId);
        return "/group/groups";
    }


    @PostMapping("/{id}/saveGroup")
    public String saveGroup(@ModelAttribute("group") Groups group,
                            @PathVariable Long id) {
        groupService.addGroup(id, group);
        return "redirect:/courses/" + id;
    }

    @GetMapping("/groups/{companyId}/{id}/addGroupByCourseId")
    public String addGroupByCourseId(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id, Model model) {
        model.addAttribute("newGroup", new Groups());
        model.addAttribute("courseId", id);
        model.addAttribute("companyId", companyId);
        return "/group/addGroupByCourse";
    }

    @PostMapping("/{courseId}/{id}/saveGroupByCourseId")
    public String saveGroupByCourseId(@ModelAttribute("group") Groups group,
                                      @PathVariable("id") Long id, @PathVariable("courseId") Long courseId) {
        groupService.addGroupByCourseId(id, courseId, group);
        return "redirect:/groups/" + id + "/" + courseId;
    }


    @PostMapping("/{companyId}/{id}/saveUpdateGroup")
    public String saveUpdateCourse(@PathVariable("companyId") Long companyId,
                                   @PathVariable("id") Long id,
                                   @ModelAttribute("group") Groups group) {
        groupService.updateGroup(group, id);
        return "redirect:/courses/" + companyId;
    }

    @GetMapping("/updateGroupByCourseId/{courseId}/{id}")
    public String updateGroupByCourseId(@PathVariable("id") Long id, @PathVariable("courseId") Long courseId, Model model) {
        Groups group = groupService.getGroupById(id);
        model.addAttribute("group", group);
        model.addAttribute("courseId", courseId);
        return "/group/updateGroup";
    }

    @PostMapping("/{courseId}/{id}/saveUpdateGroupByCourseId")
    public String saveUpdateGroupByCourseId(@PathVariable("courseId") Long courseId,
                                            @PathVariable("id") Long id,
                                            @ModelAttribute("group") Groups group) {
        Long companyId = groupService.getGroupById(id).getCompany().getId();
        groupService.updateGroup(group, id);
        return "redirect:/groups/" + companyId + "/" + courseId;
    }

    @GetMapping("/{companyId}/{id}/deleteGroup")
    public String deleteGroup(@PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        groupService.deleteGroup(id);
        return "redirect:/courses/" + companyId;
    }

    @GetMapping("/{courseId}/{id}/deleteGroupByCourseId")
    public String deleteGroupCourseId(@PathVariable("id") Long id, @PathVariable("courseId") Long courseId) {
        Long companyId = groupService.getGroupById(id).getCompany().getId();
        groupService.deleteGroup(id);
        return "redirect:/groups/" + companyId + "/" + courseId;
    }

    @PostMapping("/{groupId}/assignStudent")
    public String assignStudent(@PathVariable("groupId") Long groupId,
                                @ModelAttribute("student") Student student
    ) {
        System.out.println(student);
        studentService.assignStudent(groupId, student.getId());
        return "redirect:/students/" + groupId;
    }
}
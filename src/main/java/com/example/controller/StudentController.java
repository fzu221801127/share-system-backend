package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.entity.Student;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-01-27
 */
@RestController
@RequestMapping("/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 描述:插入新的学生对象
     * 参数:学生对象
     * 返回值:插入成功为true,失败为false
     * @author zhuangweilong
     * @since 2021-08-20
     */
    @PostMapping("")
    public boolean insertStudent(@RequestBody Student student) {
        return  this.studentService.save(student);
    }

    /**
     * 描述:通过id删除学生
     * 参数:id
     * 返回值:删除成功为true,失败为false
     * @author zhuangweilong
     * @since 2021-08-20
     */
    @DeleteMapping ("/id")
    public boolean deleteStudentById(Integer id) {
        return this.studentService.removeById(id);
    }

    /**
     * 描述:更新学生对象
     * 参数:学生对象
     * 返回值:更新成功为true,失败为false
     * @author zhuangweilong
     * @since 2021-08-20
     */
    @PutMapping("/id")
    public boolean update(@RequestBody Student student){ return this.studentService.updateById(student); }

    /**
     * 描述:获取全部学生列表
     * 参数:无
     * 返回值:学生列表
     * @author zhuangweilong
     * @since 2021-08-20
     * @return
     */
    @GetMapping("")
    public List<Student> studentList(){
        return this.studentService.list();
    }

    /**
     * 描述:通过名字模糊搜索学生
     * 参数:学生名
     * 返回值:学生列表
     * @author zhuangweilong
     * @since 2021-08-20
     */
    @GetMapping("/name")
    public List<Student> searchStudentByName(Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(
                wrapper ->
                        wrapper.like("name", student.getName())
        );
        return  this.studentService.list(queryWrapper);
    }

}


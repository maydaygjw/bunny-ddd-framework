package xyz.mayday.tools.bunny.ddd.sample.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.mayday.tools.bunny.ddd.core.controller.BaseControllerImpl;
import xyz.mayday.tools.bunny.ddd.sample.domain.TodoDO;
import xyz.mayday.tools.bunny.ddd.sample.query.TodoQuery;
import xyz.mayday.tools.bunny.ddd.sample.service.TodoService;
import xyz.mayday.tools.bunny.ddd.sample.vo.TodoVO;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static xyz.mayday.tools.bunny.ddd.schema.http.ResourceProperties.*;

/**
 * @author gejunwen
 */
@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
@Api(tags = "待办模块", consumes = "application/json", produces = "application/json")
public class TodoController extends BaseControllerImpl<Long, TodoVO, TodoQuery, TodoDO> {

    final TodoService todoService;

    @Override
    public BaseService<Long, TodoDO> getService() {
        return todoService;
    }

    @ApiOperation("查询待办")
    @GetMapping
    @Override
    public PageableData<TodoVO> queryItems(TodoQuery todoQuery, CommonQueryParam commonQueryParam) {
        return super.queryItems(todoQuery, commonQueryParam);
    }

    @GetMapping(ID_PLACEHOLDER)
    @Override
    public Optional<TodoVO> queryById(@PathVariable Long id) {
        return Optional.of(new TodoVO().withId(1L).withName("Bob").withDescription("desc"));
    }

    @GetMapping(COUNT)
    @Override
    public Long countItems(TodoQuery query) {
        return super.countItems(query);
    }

    @Override
    @GetMapping(QUERY_ALL)
    public List<TodoVO> queryAll(CommonQueryParam commonQueryParam) {
        return queryItems(null, null).getRecords();
    }

    @GetMapping(HISTORIES_BY_ID)
    @Override
    public List<TodoVO> findHistories(@PathVariable Long id) {
        return super.findHistories(id);
    }

    @DeleteMapping(ID_PLACEHOLDER)
    @Override
    public TodoVO delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @DeleteMapping
    @Override
    public List<TodoVO> bulkDelete(@RequestParam List<Long> ids) {
        return super.bulkDelete(ids);
    }

    @PostMapping
    @Override
    public TodoVO create(@RequestBody TodoVO vo) {
        return super.create(vo);
    }

    @PutMapping
    @Override
    public TodoVO update(@RequestBody TodoVO vo) {
        return super.update(vo);
    }

    @ApiOperation(value = "多文件MultipartFile上传")
//    @ApiImplicitParams({@ApiImplicitParam(name = "file[]", value = "文件流对象,接收数组格式", required = true,dataType = "MultipartFile",allowMultiple = true),
//            @ApiImplicitParam(name = "title", value = "title", required = true)}
//    )
    @RequestMapping(value="/uploadMaterial",method = RequestMethod.POST)
    @ResponseBody
    public TodoVO updateWithAttachment(@RequestParam(value="file[]",required = true) List<MultipartFile> files,@RequestParam(value = "title") String title, HttpServletRequest request) {
        return null;
    }

    @GetMapping("/withMap")
    public void withMapReturnType() {

    }
}



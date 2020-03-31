package com.huang.service;

import com.huang.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author guangtou
 * @create 2020--02--07--17:11
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    Type updateType(Long id,Type type);

    void deleteType(Long id);

    public Type getTypeByName(String name);

    /**
     * 查询size个，由大到小
     * @param size
     * @return
     */
    List<Type> listTypeTop(Integer size);
}

package com.infrastructure.spring.bind.method;

import com.extra.jquery.utils.JQueryDataTableUtils;
import com.infrastructure.common.able.Searchable;
import com.infrastructure.common.able.SimpleSearcher;
import com.infrastructure.spring.bind.annotation.SearchParam;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * SearchParamMethodArgumentResolver
 *
 * @author tyq
 * @date 2016/1/14
 */
public class SearchParamMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SearchParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> clazz = parameter.getParameterType();

        Searchable searcher = null;
        if (clazz == Searchable.class) {
            searcher = new SimpleSearcher();
        } else if (Searchable.class.isAssignableFrom(clazz)) {
            searcher = (Searchable) BeanUtils.instantiate(clazz);
        }

        if (searcher != null)  {
            SearchParam sa = parameter.getParameterAnnotation(SearchParam.class);
            Class<?> target = sa.value();
            searcher.setWhere(JQueryDataTableUtils.buildSql(webRequest.getParameterMap(), target));
        }

        return searcher;
    }
}

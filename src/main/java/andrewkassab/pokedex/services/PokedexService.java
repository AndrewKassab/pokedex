package andrewkassab.pokedex.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static andrewkassab.pokedex.constants.PageValues.DEFAULT_PAGE;
import static andrewkassab.pokedex.constants.PageValues.DEFAULT_PAGE_SIZE;

public abstract class PokedexService {

    protected PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        Sort sort = Sort.by(Sort.Order.asc("id"));

        int queryPageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

}

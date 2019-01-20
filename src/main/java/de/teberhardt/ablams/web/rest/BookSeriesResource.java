package de.teberhardt.ablams.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.teberhardt.ablams.service.BookSeriesService;
import de.teberhardt.ablams.web.rest.errors.BadRequestAlertException;
import de.teberhardt.ablams.web.rest.util.HeaderUtil;
import de.teberhardt.ablams.service.dto.BookSeriesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BookSeries.
 */
@RestController
@RequestMapping("/api")
public class BookSeriesResource {

    private final Logger log = LoggerFactory.getLogger(BookSeriesResource.class);

    private static final String ENTITY_NAME = "bookSeries";

    private final BookSeriesService bookSeriesService;

    public BookSeriesResource(BookSeriesService bookSeriesService) {
        this.bookSeriesService = bookSeriesService;
    }

    /**
     * POST  /book-series : Create a new bookSeries.
     *
     * @param bookSeriesDTO the bookSeriesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookSeriesDTO, or with status 400 (Bad Request) if the bookSeries has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/book-series")
    @Timed
    public ResponseEntity<BookSeriesDTO> createBookSeries(@RequestBody BookSeriesDTO bookSeriesDTO) throws URISyntaxException {
        log.debug("REST request to save BookSeries : {}", bookSeriesDTO);
        if (bookSeriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookSeries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookSeriesDTO result = bookSeriesService.save(bookSeriesDTO);
        return ResponseEntity.created(new URI("/api/book-series/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /book-series : Updates an existing bookSeries.
     *
     * @param bookSeriesDTO the bookSeriesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookSeriesDTO,
     * or with status 400 (Bad Request) if the bookSeriesDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookSeriesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/book-series")
    @Timed
    public ResponseEntity<BookSeriesDTO> updateBookSeries(@RequestBody BookSeriesDTO bookSeriesDTO) throws URISyntaxException {
        log.debug("REST request to update BookSeries : {}", bookSeriesDTO);
        if (bookSeriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BookSeriesDTO result = bookSeriesService.save(bookSeriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookSeriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /book-series : get all the bookSeries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bookSeries in body
     */
    @GetMapping("/book-series")
    @Timed
    public List<BookSeriesDTO> getAllBookSeries() {
        log.debug("REST request to get all BookSeries");
        return bookSeriesService.findAll();
    }

    /**
     * GET  /book-series/:id : get the "id" bookSeries.
     *
     * @param id the id of the bookSeriesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookSeriesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/book-series/{id}")
    @Timed
    public ResponseEntity<BookSeriesDTO> getBookSeries(@PathVariable Long id) {
        log.debug("REST request to get BookSeries : {}", id);
        Optional<BookSeriesDTO> bookSeriesDTO = bookSeriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookSeriesDTO);
    }

    /**
     * DELETE  /book-series/:id : delete the "id" bookSeries.
     *
     * @param id the id of the bookSeriesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/book-series/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookSeries(@PathVariable Long id) {
        log.debug("REST request to delete BookSeries : {}", id);
        bookSeriesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

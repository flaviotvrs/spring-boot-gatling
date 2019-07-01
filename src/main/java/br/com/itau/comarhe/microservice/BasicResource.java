package br.com.itau.comarhe.microservice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("api/service")
public class BasicResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BasicResource.class);

	@GetMapping(value = "/sync")
	public ResponseEntity<String> sync(@RequestParam(name = "minMs", required = true) long minMs,
			@RequestParam(name = "maxMs", required = true) long maxMs,
			@RequestParam(name = "successRatio", required = true) double successRatio) {
		
		LOGGER.info("Sync method: minMs: {}, maxMs: {}, successRatio: {}", minMs, maxMs, successRatio);
		
		return handle(minMs, maxMs, successRatio);
	}

	
	@GetMapping(value = "/async")
	public DeferredResult<ResponseEntity<String>> async(@RequestParam(name = "minMs", required = true) long minMs,
			@RequestParam(name = "maxMs", required = true) long maxMs,
			@RequestParam(name = "successRatio", required = true) double successRatio) {
		
		LOGGER.info("Async method: minMs: {}, maxMs: {}, successRatio: {}", minMs, maxMs, successRatio);
		
		DeferredResult<ResponseEntity<String>> output = new DeferredResult<>();
		output.setResult(handle(minMs, maxMs, successRatio));
		
		return output;
	}
	
	private ResponseEntity<String> handle(long minMs, long maxMs, double successRatio) {

		try {
			TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(minMs, maxMs));
		} catch (InterruptedException e) {
		}

		boolean success = ThreadLocalRandom.current().nextDouble(0.0, 1.0) > (1 - successRatio);
		return new ResponseEntity<String>("Done: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
				success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}

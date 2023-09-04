package com.mapping.notionpage;

import com.mapping.notionpage.dto.CreatePageDTO;
import com.mapping.notionpage.dto.PageDTO;
import com.mapping.notionpage.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class NotionpageApplication {

	@Autowired
	private PageService pageService;

	public static void main(String[] args) {
		SpringApplication.run(NotionpageApplication.class, args);

		// 테스트 코드 직접 실행
		testCreateAndRetrievePages();
	}

	@Bean
	@Profile("test")
	public void init() {
		// 여기에서 테스트 데이터 초기화 또는 더미 데이터 생성 가능
	}

	public static void testCreateAndRetrievePages() {
		PageService pageService = SpringApplication.run(NotionpageApplication.class).getBean(PageService.class);
		Random random = new Random();
		long topNodePageId = 1L;
		List<Long> pageIds = new ArrayList<>();

		// 페이지 생성
		for (int i = 0; i < 10; i++) {
			String title = "페이지 제목 " + (i + 1);
			String content = "페이지 내용 " + (i + 1);

			// parentPageId 설정: 앞에 저장된 페이지 중에서 무작위로 선택
			Long parentId = (pageIds.isEmpty() || random.nextBoolean()) ? null : pageIds.get(random.nextInt(pageIds.size()));

			CreatePageDTO createPageDTO = new CreatePageDTO(title, content, topNodePageId, parentId);
			long pageId = pageService.createPage(createPageDTO);
			pageIds.add(pageId);

			log.info("페이지 생성: pageId={}, title={}, content={}, topNode={}, parentPageId={}",
					pageId, title, content, topNodePageId, parentId);
		}

		// 생성된 페이지 중에서 1개 무작위로 조회
		int randomPageId = random.nextInt(10) + 1;
		String pageInfo = pageService.getPageInfo(pageIds.get(randomPageId - 1));

		log.info(pageInfo);
	}
}
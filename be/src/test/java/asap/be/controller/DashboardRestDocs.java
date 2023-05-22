package asap.be.controller;

import asap.be.dto.DashboardDto;
import asap.be.service.DashBoardService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static asap.be.utils.ApiDocumentUtils.getDocumentResponse;
import static asap.be.utils.MainControllerConstants.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class DashboardRestDocs {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Gson gson;

	@MockBean
	private DashBoardService dashBoardService;


	@Test
	@DisplayName("아이템 별 최신 21일 입/출고량 테스트")
	void getProductCntByDate() throws Exception {

		String pName = "소금";
		given(dashBoardService.CntProduct(anyString())).willReturn(PRODUCT_CNT_DTO_LIST);
		//when
		ResultActions actions =
				mockMvc.perform(
						RestDocumentationRequestBuilders.get("/api/cnt-product-by-date?pName={pName}", pName)
								.accept(MediaType.APPLICATION_JSON)
				);
		//then
		actions.andExpect(status().isOk())
				.andDo(document(
						"cnt-product-by-date",
						getDocumentResponse(),
						responseFields(
								List.of(
										fieldWithPath("[]").type(JsonFieldType.ARRAY).description("결과 데이터"),
										fieldWithPath("[].date").type(JsonFieldType.STRING).description("날짜"),
										fieldWithPath("[].insertCnt").type(JsonFieldType.NUMBER).description("입고량"),
										fieldWithPath("[].releaseCnt").type(JsonFieldType.NUMBER).description("출고량")
								)
						)
				));
	}

	@Test
	@DisplayName("일별 상품 입/출고수 TOP 10 테스트")
	void getProductRankTop10() throws Exception {

		List<Map<String, Object>> insertRankDto = new ArrayList<>();
		Map<String, Object> insertRank = new HashMap<>();
		insertRank.put("pName", "입고 상품명");
		insertRank.put("insertCnt", 100);
		insertRank.put("ranking", 1);
		insertRankDto.add(insertRank);

		List<Map<String, Object>> releaseRankDto = new ArrayList<>();
		Map<String, Object> releaseRank = new HashMap<>();
		releaseRank.put("pName", "출고 상품명");
		releaseRank.put("releaseCnt", 100);
		releaseRank.put("ranking", 1);
		releaseRankDto.add(releaseRank);

		DashboardDto.RankDto rankDto = DashboardDto.RankDto.builder()
				.insertRankDto(insertRankDto)
				.releaseRankDto(releaseRankDto)
				.build();

		given(dashBoardService.ProductCntRank()).willReturn(rankDto);

		ResultActions actions =
				mockMvc.perform(
						RestDocumentationRequestBuilders.get("/api/product-rank")
								.accept(MediaType.APPLICATION_JSON)
				);

		actions.andExpect(status().isOk())
				.andDo(document(
						"product-rank",
						getDocumentResponse(),
						responseFields(
								List.of(
										fieldWithPath("insertRankDto[]").type(JsonFieldType.ARRAY).description("금일 상품별 입고량 TOP 10"),
										fieldWithPath("insertRankDto[].pName").type(JsonFieldType.STRING).description("입고 상품명"),
										fieldWithPath("insertRankDto[].insertCnt").type(JsonFieldType.NUMBER).description("금일 입고량"),
										fieldWithPath("insertRankDto[].ranking").type(JsonFieldType.NUMBER).description("순위"),
										fieldWithPath("releaseRankDto[]").type(JsonFieldType.ARRAY).description("금일 상품별 출고량 TOP 10"),
										fieldWithPath("releaseRankDto[].pName").type(JsonFieldType.STRING).description("출고 상품명"),
										fieldWithPath("releaseRankDto[].releaseCnt").type(JsonFieldType.NUMBER).description("금일 출고량"),
										fieldWithPath("releaseRankDto[].ranking").type(JsonFieldType.NUMBER).description("순위")
								)
						)
				));
	}

	@Test
	@DisplayName("월별 재고 현황 테스트")
	void getMonthlyStockSum() throws Exception {

		String year = "2022";

		given(dashBoardService.getMonthlyStockSummary(anyString())).willReturn(YEAR_STATUS_DTO_LIST);

		ResultActions actions =
				mockMvc.perform(
						RestDocumentationRequestBuilders.get("/api/monthly-stock-summary?year={year}", year)
								.accept(MediaType.APPLICATION_JSON)
				);

		actions.andExpect(status().isOk())
				.andDo(document(
						"monthly-stock-summary",
						getDocumentResponse(),
						responseFields(
								List.of(
										fieldWithPath("[]").type(JsonFieldType.ARRAY).description("결과 데이터"),
										fieldWithPath("[].month").type(JsonFieldType.NUMBER).description("월"),
										fieldWithPath("[].total_cnt").type(JsonFieldType.NUMBER).description("월 전체 수량"),
										fieldWithPath("[].total_quantity").type(JsonFieldType.NUMBER).description("월 전체 출고량"),
										fieldWithPath("[].total_pInsert").type(JsonFieldType.NUMBER).description("월 전체 입고량")
								)
						)
				));
	}

	@Test
	@DisplayName("나라 별 재고 분포도 테스트")
	void getCountryProductStatus() throws Exception {

		given(dashBoardService.getCountryProductStatus()).willReturn(COUNTRY_DTO_LIST);

		ResultActions actions =
				mockMvc.perform(RestDocumentationRequestBuilders.get("/api/country-product-status")
						.accept(MediaType.APPLICATION_JSON));

		actions.andExpect(status().isOk())
				.andDo(document(
						"country-product-status",
						getDocumentResponse(),
						responseFields(
								List.of(
										fieldWithPath("[]").type(JsonFieldType.ARRAY).description("결과 데이터"),
										fieldWithPath("[].countryName").type(JsonFieldType.STRING).description("나라 이름"),
										fieldWithPath("[].productCnt").type(JsonFieldType.NUMBER).description("재고 분포 개수")
								)
						)
				));

	}

	@Test
	@DisplayName("날짜별로 얻어낸 수익 측정")
	void eachDateTotalProductAmount() throws Exception {
		String start = "2023-01-01";
		String end = "2023-01-10";
		//G
		given(dashBoardService.TotalProductAmount(anyString(), anyString())).willReturn(MONEY_DTO_LIST);

		//W
		ResultActions actions =
				mockMvc.perform(RestDocumentationRequestBuilders.get("/api/total-product-amount?startDate={start}&endDate={end}", start, end)
						.accept(MediaType.APPLICATION_JSON));
		//T
		actions.andExpect(status().isOk())
				.andDo(document("total-product-amount",
						getDocumentResponse(),
						responseFields(
								List.of(
										fieldWithPath("[]").type(JsonFieldType.ARRAY).description("결과데이터"),
										fieldWithPath("[].releaseat").type(JsonFieldType.STRING).description("해당 날짜"),
										fieldWithPath("[].money").type(JsonFieldType.NUMBER).description("금액")
								)
						)));

	}

}

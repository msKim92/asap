package asap.be.repository.mybatis;

import asap.be.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
	void insertOrUpdateStock(@Param("p") PostProductDto dto, @Param("n") String today); // 상품 등록 및 입고

	void insertOrUpdateRelease(@Param("p") PostProductDto dto); // 상품 출고

	void updateProduct(@Param("p") EditProductDto dto);

	EverythingDto findById(@Param("pId") Long pId, @Param("sId") Long sId);

	List<EverythingDto> findByName(String pName);

    List<EverythingPageDto> findByAll(Integer lastId);

    AllProductCntDto findAllCntByPName(String pName);

    List<DetailInfoDto> detailPageUsingPId(Long pId);

	Boolean verifiedProduct(Long pId, Long sId);

	Boolean checkExistence(String pName);

	Long findPIdByPNameAndWId(String pName, Long wId);
}


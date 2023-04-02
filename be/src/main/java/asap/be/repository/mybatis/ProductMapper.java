package asap.be.repository.mybatis;


import asap.be.dto.EverythingDto;
import asap.be.dto.PostProductDto;
import asap.be.dto.RequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    void save(PostProductDto productDto); //상품등록
    void status(@Param("p") RequestDto.UpdatePStatus requestDto);
    void name(@Param("p") RequestDto.UpdatePName requestDto);
    void price(@Param("p") RequestDto.UpdatePrice requestDto);
    void barcode(@Param("p") RequestDto.UpdatePCode requestDto);
    EverythingDto findById(@Param("pId") Long pId, @Param("sId") Long sId);
    List<EverythingDto> findByName(String pName);
    List<EverythingDto> findByAll();
    boolean existProductByNameAndWId(@Param("pName") String pName, @Param("wId") Long wId);
}


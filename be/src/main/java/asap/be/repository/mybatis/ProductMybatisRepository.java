package asap.be.repository.mybatis;

import asap.be.dto.*;
import asap.be.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductMybatisRepository implements ProductRepository {

	private final ProductMapper productMapper;

	@Override
	@Transactional
	public void insertOrUpdateStock(PostProductDto dto) {
		String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String lastReceiveIn = findLastReceiveIn(dto.getpCode());

		productMapper.insertOrUpdateStock(dto, lastReceiveIn != null ? lastReceiveIn : today);
	}

	@Override
	public void insertOrUpdateRelease(PostProductDto dto) {
		productMapper.insertOrUpdateRelease(dto);
	}

	@Override
	public void updateProduct(EditProductDto dto) {
		productMapper.updateProduct(dto);
	}

	@Override
	public EverythingDto findById(Long pId, Long sId) {
		return productMapper.findById(pId, sId);
	}

	@Override
	public List<EverythingDto> findByName(String pName) {
		return productMapper.findByName(pName);
	}

    @Override
    public List<EverythingPageDto> findByAll(Integer lastId, String order) {
        return productMapper.findByAll(lastId, order);
    }

    @Override
    public AllProductCntDto findAllCntByPName(String pName) {
        return productMapper.findAllCntByPName(pName);
    }

	@Override
	public List<DetailReleaseDto> detailReleaseUsingPId(Long pId) {
		return productMapper.detailReleaseUsingPId(pId);
	}

	@Override
	public List<DetailInsertDto> detailInsertUsingPId(Long pId) {
		return productMapper.detailInsertUsingPId(pId);
	}


	@Override
	public Boolean verifiedProduct(Long pId, Long sId) {
		return productMapper.verifiedProduct(pId, sId);
	}

	@Override
	public Boolean checkExistence(String pName) {
		return productMapper.checkExistence(pName);
	}

	@Override
	public Long findPIdByPNameAndWId(String pName, Long wId) {
		return productMapper.findPIdByPNameAndWId(pName, wId);
	}

	@Override
	public List<String> showAllPName() {
		return productMapper.showAllPName();
	}

	@Override
	public DetailProductDto findProductById(Long pId) {
		return productMapper.findProductById(pId);
	}

	@Override
	public Long findSIdByPNameAndWId(String pName, Long wId) {
		return productMapper.findSIdByPNameAndWId(pName, wId);
	}

	@Override
	public List<DetailWarehouseDto> findProductWarehouseById(Long pId) {
		return productMapper.findProductWarehouseById(pId);
	}

	@Override
	public Long findByUUID(String uuid) {
		return productMapper.findByUUID(uuid);
	}

	@Override
	public void saveS3ImageUrl(String imageURL, Long pId) {
		productMapper.saveS3ImageUrl(imageURL, pId);
	}

	@Override
	public List<EverythingPageDto> search(Integer lastId, String pName, String order) {
		return productMapper.search(lastId, pName, order);
	}

	@Override
	public String findLastReceiveIn(String uuid) {
		return productMapper.findLastReceiveIn(uuid);
	}

}

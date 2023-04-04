package asap.be.service;

import asap.be.domain.Release;
import asap.be.domain.Stock;
import asap.be.dto.EverythingDto;

import java.util.List;

public interface ReleaseService {
	List<Release> findAll();

	List<Release> findReleaseById(Long sId);

	Stock findStockByPId(Long pId);

	EverythingDto findStockByPNameAndWId(String pName, Long wId, String pCode);

}

package com.example.demo.domains.member.service.interfaces;

import com.example.demo.domains.member.dto.ShoppingOrderDTO;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.entity.ShoppingOrder;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.repository.ShoppingOrderRepository;
import com.example.demo.domains.member.service.impls.ShoppingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author : 나선주
 * date : 2024-09-24
 * description : MemberService
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24       나선주          최초 생성
 * 2024-09-24       나선주          method생성(CRD)
 */
@Service
public class ShoppingOrderServiceImpl implements ShoppingOrderService {
    @Autowired
    private ShoppingOrderRepository shoppingOrderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ShoppingOrderProductServiceImpl shoppingOrderProductServiceImpl;

    @Override
    public ShoppingOrder saveOrder(ShoppingOrder shoppingOrder) {
        ShoppingOrder save = shoppingOrderRepository.save(shoppingOrder);
        return save;
    }

    @Override
    public List<ShoppingOrder> findAllOrders() {
        List<ShoppingOrder> all = shoppingOrderRepository.findAll();
        return all;
    }

    @Override
    public Boolean deleteOrder(ShoppingOrder shoppingOrder) {
        try{
            shoppingOrderRepository.delete(shoppingOrder);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public List<ShoppingOrderDTO> getShoppingOrderByMemberId(Long memberId) {
        List<ShoppingOrder> orders = shoppingOrderRepository.findAllByMember(memberId);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ShoppingOrderDTO convertToDTO(ShoppingOrder order) {
        ShoppingOrderDTO dto = new ShoppingOrderDTO();
        dto.setId(order.getId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setIsAllShipping(order.getIsAllShipping());
        dto.setReceipientName(order.getReceipient_name());
        dto.setReceipientTelNum(order.getReceipient_telNum());
        dto.setBuyingDate(order.getBuyingDate());
        dto.setMemo(order.getMemo());
        dto.setLocation(order.getMyLocation().getAddress());
        dto.setOrderProductList(order.getOrderProductList().stream()
                .map(shoppingOrderProduct -> shoppingOrderProductServiceImpl.convertOrderProductToDTO(shoppingOrderProduct))
                .collect(Collectors.toList()));
        return dto;
    }
}


SET FOREIGN_KEY_CHECKS=0;

UPDATE relationship_type SET b_is_to_a = 'Case' WHERE uuid = '9065e3c6-b2f5-4f99-9cbf-f67fd9f82ec5';

UPDATE relationship r
	INNER JOIN relationship_type t ON r.relationship = t.relationship_type_id
	SET r.person_a = r.person_b , r.person_b = r.person_a
    WHERE  t.uuid = '9065e3c6-b2f5-4f99-9cbf-f67fd9f82ec5';
      
SET FOREIGN_KEY_CHECKS=1;
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

delete
from auth_user;

insert into auth_user(id, created_at, updated_at, username, nickname, email, phone, account_non_expired,
                      account_non_locked,
                      enabled)
values (-1, now(), now(), 'wenhailin', 'wenslo', 'xxx@gmai.com', '111111111', true, true, true)
// @ts-ignore
/* eslint-disable */

declare namespace Protocol {

    type RestResult = {
        success?: boolean;
        data?: any;
        errorMessage?: string;
    };

    type PageResult = {
        success?: boolean;
        data?: {
            list?: any;
            current?: number;
            pageSize?: number;
            total?: number;    
        };
        errorMessage?: string;
    };

}
